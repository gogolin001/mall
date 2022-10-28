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

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysAuthorityService authorityService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> userService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<SysAuthority> resourceList = authorityService.listAll();
            for (SysAuthority resource : resourceList) {
                map.put(resource.getBgUri(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }
}
