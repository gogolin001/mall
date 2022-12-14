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
import jakarta.servlet.http.HttpServletRequest;
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
import java.time.Duration;
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
        //????????????
        QuickConfig qc1 = QuickConfig.newBuilder("user:")
                .expire(Duration.ofDays(1))
                .cacheType(CacheType.REMOTE) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        userCache = cacheManager.getOrCreateCache(qc1);
        userCache.config().setLoader(userMapper::selectByUserName);

        //token??????
        QuickConfig qc2 = QuickConfig.newBuilder("userToken:")
                .expire(Duration.ofDays(30))
                .cacheType(CacheType.REMOTE) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        tokenCache = cacheManager.getOrCreateCache(qc2);
        tokenCache.config().setLoader(userTokenMapper::selectById);
    }

    /**
     * ??????????????????
     * @return ????????????
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
     * ???????????????????????????
     * @param username ?????????
     * @return ????????????
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
     * ????????????
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
     * ????????????
     * @param username ?????????
     * @param password ??????
     * @return ?????????JWT???token
     */
    public String login(String username, String password){
        String token = "";
        UserDetails userDetails = loadUserByUsername(username);
        if(ObjectUtil.isEmpty(userDetails)){
            Asserts.fail("???????????????");
        }
        else if(!passwordEncoder.matches(Base64.decodeStr(password),userDetails.getPassword())) {
            Asserts.fail("????????????");
        }
        else if(!userDetails.isEnabled()){
            Asserts.fail("??????????????????");
        }
        else{
            token = handleToken(userDetails);
        }
        return token;
    }

    /**
     * ??????unionId???mpOpenId???miniOpenId??????
     * @param openId
     * @return
     */
    public String longByOpenId(String openId){
        SysUser user = userMapper.selectByOpenId(openId);
        if(ObjectUtil.isNull(user)){
            Asserts.fail("???????????????");
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
                .setIp(RequestUtil.getRequestIp(request));

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
     * ??????token?????????
     * @param oldToken ??????token
     */
    public String refreshToken(String oldToken){
        if(StrUtil.isEmpty(oldToken)){
            //jwtTokenUtil.getUserNameFromToken();
        }
        return "";
    }

    /**
     * ????????????id????????????
     */
    public SysUser getItem(Long id){
        return null;
    }

    /**
     * ??????????????????????????????????????????
     */
    public IPage<SysUser> list(String keyword, Integer pageSize, Integer pageNum){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(keyword),"username", keyword).like(!StrUtil.isEmpty(keyword),"rename", keyword);
        return userMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
    }

    /**
     * ????????????????????????
     */
    public int update(Long id, SysUser admin){
        return 0;
    }

    /**
     * ??????????????????
     */
    public int delete(Long id){
       return 0;
    }

    /**
     * ????????????????????????
     */
    @Transactional
    public int updateRole(Long adminId, List<Long> roleIds){
        return 0;
    }

    /**
     * ????????????????????????
     */
    public List<SysRole> getRoleList(Long adminId){
        return null;
    }

    /**
     * ??????????????????????????????
     * @param userId
     */
    public Set<SysAuthority> getMenu(Long userId){
        return null;
    }

    /**
     * ????????????????????????
     * @param userId
     * @param authorityPid
     */
    public void getButton(Long userId, Long authorityPid){

    }


    /**
     * ????????????id???????????????????????????????????????????????????????????????
     * @param user ??????
     * @return ?????????????????????
     */
    public Set<String> getResourceList(SysUser user){
        if(CollUtil.isEmpty(user.getRoleIdList())){
            return null;
        }
        return roleService.listResource(user.getRoleIdList());
    }

    /**
     * ????????????
     */
    public int updatePassword(UpdateUserPasswordParam updateUserPasswordParam){
        return 0;
    }

    /**
     * ??????????????????
     */
    public UserDetails loadUserByUsername(String username){
        //??????????????????
        SysUser user = getUserByUsername(username);
        if (user != null) {
            Set<String> resourceList = getResourceList(user);
            return new AdminUserDetails(user,resourceList);
        }
        throw new UsernameNotFoundException("????????????????????????");
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
