package com.lam.mall.admin.service;

import cn.hutool.core.collection.CollUtil;
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


    public SysUser getAdminByUsername(String username) {
        /* 从缓存中取如果缓存中有直接返回
        SysUser admin = getCacheService().getAdmin(username);
        if(admin!=null) return  admin;
        */
        SysUser admin = userMapper.SelectByUserName(username);
        if (admin != null) {
            // 设置缓存
            // getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

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
