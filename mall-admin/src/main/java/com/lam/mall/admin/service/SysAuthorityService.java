package com.lam.mall.admin.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.lam.mall.mbg.maper.SysAuthorityMapper;
import com.lam.mall.mbg.model.SysAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAuthorityService {

    @Autowired
    private SysAuthorityMapper authorityMapper;


    @Cached(name="authority-", expire = 3600, cacheType = CacheType.BOTH)
    public List<SysAuthority> listAll(){
        return authorityMapper.list();
    }
}
