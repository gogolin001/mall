package com.lam.mall.admin.service;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.lam.mall.admin.bo.AdminUserDetails;
import com.lam.mall.mbg.maper.SysUserMapper;
import com.lam.mall.mbg.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Cached(name="user-", key="#username", expire = 3600, cacheType = CacheType.BOTH)
    public SysUser getAdminByUsername(String username) {
        SysUser admin = userMapper.SelectByUserName(username);
        if (admin != null) {
            return admin;
        }
        return null;
    }

    /**
     * 根据用户id获取具有权限的接口（同时根据用户角色返回）
     * @param adminId
     * @return
     */
    public List<String> getResourceList(Long adminId) {
        List<String> resourceList = getCacheService().getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            getCacheService().setResourceList(adminId,resourceList);
        }
        return resourceList;
    }


    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        SysUser admin = getAdminByUsername(username);
        if (admin != null) {
            List<String> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
