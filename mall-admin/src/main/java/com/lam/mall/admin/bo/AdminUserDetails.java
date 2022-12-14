package com.lam.mall.admin.bo;

import cn.hutool.core.collection.CollectionUtil;
import com.lam.mall.mbg.model.sys.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails {
    //后台用户
    private SysUser sysUser;
    //拥有资源列表
    private Set<String> authorityList;
    public AdminUserDetails(SysUser sysUser, Set<String> resourceList) {
        this.sysUser = sysUser;
        this.authorityList = resourceList;
    }

    /**
     * 返回权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        if(!CollectionUtil.isEmpty(this.authorityList)){
            authorities.addAll(authorityList.stream().map(SimpleGrantedAuthority::new).toList());
        }
        //返回当前用户的角色和权限
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus();
    }

    public SysUser getSysUser(){
        return sysUser;
    }
}