package com.lam.mall.admin.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.lam.mall.mbg.maper.sys.SysAuthorityMapper;
import com.lam.mall.mbg.model.sys.SysAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SysAuthorityService {

    private final SysAuthorityMapper authorityMapper;

    private final CacheManager cacheManager;
    private Cache<Long, SysAuthority> authorityCache;
    @Autowired
    private SysAuthorityService(SysAuthorityMapper sysAuthorityMapper, CacheManager cacheManager){
        this.authorityMapper = sysAuthorityMapper;
        this.cacheManager = cacheManager;
    }

    @PostConstruct
    public void init() {
        QuickConfig qc = QuickConfig.newBuilder("authority")
                //.expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        authorityCache = cacheManager.getOrCreateCache(qc);
        authorityCache.config().setLoader(authorityMapper::selectById);
    }


    public List<SysAuthority> listAll(){

        var data = authorityCache.getAll(null);
        return authorityMapper.list();
    }
}
