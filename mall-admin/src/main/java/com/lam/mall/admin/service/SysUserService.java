package com.lam.mall.admin.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lam.mall.admin.bo.AdminUserDetails;
import com.lam.mall.admin.dto.UpdateUserPasswordParam;
import com.lam.mall.admin.dto.UserParam;
import com.lam.mall.common.exception.Asserts;
import com.lam.mall.common.util.JwtTokenUtil;
import com.lam.mall.mbg.mapper.sys.SysUserMapper;
import com.lam.mall.mbg.model.sys.SysRole;
import com.lam.mall.mbg.model.sys.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SysUserMapper userMapper;

    @Autowired
    public SysUserService(SysUserMapper sysUserMapper, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder){
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = sysUserMapper;
    }


    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    @Cached(name="user-", key="#username", expire = 3600, cacheType = CacheType.BOTH)
    public SysUser getAdminByUsername(String username) {
        return userMapper.selectByUserName(username);
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
        return null;
    }

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    public String login(String username, String password){
        String token = "";
        try{
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
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                token = jwtTokenUtil.generateToken(userDetails);
            }
        }
        catch(Exception e) {
            Asserts.fail("系统异常！");
        }
        return token;
    }

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    public String refreshToken(String oldToken){
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
     * 根据用户id获取具有权限的接口（同时根据用户角色返回）
     * @param adminId 用户id
     * @return 用户具有的权限
     */
    public List<String> getResourceList(Long adminId){
        return null;
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
        SysUser user = getAdminByUsername(username);
        if (user != null) {
            List<String> resourceList = getResourceList(user.getId());
            return new AdminUserDetails(user,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
