package com.lam.mall.admin.config;

import com.lam.mall.mbg.model.sys.SysAuthority;
import com.lam.mall.common.component.DynamicSecurityService;
import com.lam.mall.admin.service.SysUserService;
import com.lam.mall.admin.service.SysAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * Created by macro on 2019/11/9.
 */
@Configuration
public class MallSecurityConfig {
    private final SysUserService userService;

    private final SysAuthorityService authorityService;

    @Autowired
    public MallSecurityConfig(SysUserService userService, SysAuthorityService authorityService){
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return userService::loadUserByUsername;
    }

    /**
     * 动态权限配置（配置需要权限的路径）
     * @return
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            Map<String,String> resourceList = authorityService.getResources();
            resourceList.forEach((key,value)->{
                map.put(value, new org.springframework.security.access.SecurityConfig(key));
            });
            return map;
        };
    }
}
