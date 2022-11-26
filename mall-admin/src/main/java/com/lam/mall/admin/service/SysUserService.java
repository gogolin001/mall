package com.lam.mall.admin.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lam.mall.admin.bo.AdminUserDetails;
import com.lam.mall.admin.dto.UpdateUserPasswordParam;
import com.lam.mall.admin.dto.UserParam;
import com.lam.mall.common.exception.Asserts;
import com.lam.mall.common.util.JwtTokenUtil;
import com.lam.mall.common.util.RequestUtil;
import com.lam.mall.mbg.mapper.sys.SysUserMapper;
import com.lam.mall.mbg.mapper.sys.SysUserTokenMapper;
import com.lam.mall.mbg.model.sys.SysAuthority;
import com.lam.mall.mbg.model.sys.SysRole;
import com.lam.mall.mbg.model.sys.SysUser;
import com.lam.mall.mbg.model.sys.SysUserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SysUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserService.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder passwordEncoder;

    private final SysUserMapper userMapper;

    private final SysUserTokenMapper userTokenMapper;

    private final CacheManager cacheManager;

    private Cache<String, SysUser> userCache;

    private Cache<String, SysUserToken> tokenCache;

    private final RoleService roleService;

    private final HttpServletRequest request;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public SysUserService(JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, CacheManager cacheManager, SysUserMapper sysUserMapper, SysUserTokenMapper sysUserTokenMapper, RoleService roleService, HttpServletRequest request){
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
        this.userMapper = sysUserMapper;
        this.userTokenMapper = sysUserTokenMapper;
        this.roleService = roleService;
        this.request = request;
    }

    @PostConstruct
    public void init() {
        //用户缓存
        QuickConfig qc1 = QuickConfig.newBuilder("user:")
                //.expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        userCache = cacheManager.getOrCreateCache(qc1);
        userCache.config().setLoader(userMapper::selectByUserName);

        //token缓存
        QuickConfig qc2 = QuickConfig.newBuilder("userToken:")
                //.expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        tokenCache = cacheManager.getOrCreateCache(qc2);
        tokenCache.config().setLoader(userTokenMapper::selectById);
    }

    /**
     * 获取当前用户
     * @return 当前用户
     */
    public SysUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(ObjectUtil.isNull(auth)){
            return null;
        }
        AdminUserDetails memberDetails = (AdminUserDetails) auth.getPrincipal();
        return memberDetails.getSysUser();
    }


    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    public SysUser getUserByUsername(String username) {
        var user = userCache.get(username);
        if(ObjectUtil.isNull(user)){
            user = userMapper.selectByUserName(username);
            if(ObjectUtil.isNull(user)){
                return null;
            }
            userCache.put(username, user);
        }
        return user;
    }

    /**
     * 注册功能
     */
    public SysUser register(UserParam userParam){
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userParam, user);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    public String login(String username, String password){
        String token = "";
        UserDetails userDetails = loadUserByUsername(username);
        if(ObjectUtil.isEmpty(userDetails)){
            Asserts.fail("账号不存在");
        }
        else if(!passwordEncoder.matches(Base64.decodeStr(password),userDetails.getPassword())) {
            Asserts.fail("密码错误");
        }
        else if(!userDetails.isEnabled()){
            Asserts.fail("帐号已被禁用");
        }
        else{
            token = handleToken(userDetails);
        }
        return token;
    }

    /**
     * 使用unionId、mpOpenId、miniOpenId登录
     * @param openId
     * @return
     */
    public String longByOpenId(String openId){
        SysUser user = userMapper.selectByOpenId(openId);
        if(ObjectUtil.isNull(user)){
            Asserts.fail("未绑定用户");
        }
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        return handleToken(userDetails);
    }

    private String handleToken(UserDetails userDetails){
        String token = "";
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtTokenUtil.generateToken(userDetails);

        //do token
        SysUserToken userToken = getToken(userDetails.getUsername(),request.getHeader("client"));
        if(ObjectUtil.isNull(userToken)){
            userToken = SysUserToken.builder().username(userDetails.getUsername()).clientType(request.getHeader("client")).build();
        }
        UserAgent ua = UserAgentUtil.parse(request.getHeader("user-agent"));
        userToken.setToken(token)
                .setLoginTime(LocalDateTime.now())
                .setLastAccessTime(LocalDateTime.now())
                .setUseragent(request.getHeader("user-agent"))
                .setOs(ua.getOs().toString())
                .setBrowser(ua.getBrowser().toString()+ua.getVersion())
                .setIp(ServletUtil.getClientIP(request));

        if(ObjectUtil.isNull(userToken.getId()) ){
            userTokenMapper.insert(userToken);
        }
        else{
            userTokenMapper.updateById(userToken);
        }
        tokenCache.put(userDetails.getUsername()+":"+request.getHeader("client"), userToken);
        return token;
    }

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    public String refreshToken(String oldToken){
        if(StrUtil.isEmpty(oldToken)){
            //jwtTokenUtil.getUserNameFromToken();
        }
        return "";
    }

    /**
     * 根据用户id获取用户
     */
    public SysUser getItem(Long id){
        return null;
    }

    /**
     * 根据用户名或昵称分页查询用户
     */
    public IPage<SysUser> list(String keyword, Integer pageSize, Integer pageNum){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(keyword),"username", keyword).like(!StrUtil.isEmpty(keyword),"rename", keyword);
        return userMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
    }

    /**
     * 修改指定用户信息
     */
    public int update(Long id, SysUser admin){
        return 0;
    }

    /**
     * 删除指定用户
     */
    public int delete(Long id){
       return 0;
    }

    /**
     * 修改用户角色关系
     */
    @Transactional
    public int updateRole(Long adminId, List<Long> roleIds){
        return 0;
    }

    /**
     * 获取用户对应角色
     */
    public List<SysRole> getRoleList(Long adminId){
        return null;
    }

    /**
     * 获取具有权限目录菜单
     * @param userId
     */
    public Set<SysAuthority> getMenu(Long userId){
        return null;
    }

    /**
     * 获取具有权限按钮
     * @param userId
     * @param authorityPid
     */
    public void getButton(Long userId, Long authorityPid){

    }


    /**
     * 根据用户id获取具有权限的接口（同时根据用户角色返回）
     * @param user 用户
     * @return 用户具有的权限
     */
    public Set<String> getResourceList(SysUser user){
        if(CollUtil.isEmpty(user.getRoleIdList())){
            return null;
        }
        return roleService.listResource(user.getRoleIdList());
    }

    /**
     * 修改密码
     */
    public int updatePassword(UpdateUserPasswordParam updateUserPasswordParam){
        return 0;
    }

    /**
     * 获取用户信息
     */
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        SysUser user = getUserByUsername(username);
        if (user != null) {
            Set<String> resourceList = getResourceList(user);
            return new AdminUserDetails(user,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public SysUserToken getToken(String username, String client){
        SysUserToken userToken = tokenCache.get(username+":"+client);
        if(ObjectUtil.isNull(userToken)){
            userToken = userTokenMapper.SelectByUsernameAndClient(username, client);
            if(ObjectUtil.isNull(userToken)){
                return null;
            }
            tokenCache.put(username+":"+client,userToken);
        }
        return userToken;
    }

    public boolean tokenValidate(String username, String token){
        SysUserToken userToken = getToken(username, request.getHeader("client"));
        if(ObjectUtil.isNull(userToken) || !token.substring(this.tokenHead.length()).equals(userToken.getToken())){
            return false;
        }
        return true;
    }
}
