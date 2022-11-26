package com.lam.mall.admin.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.template.QuickConfig;
import com.lam.mall.mbg.mapper.sys.SysAuthorityMapper;
import com.lam.mall.mbg.model.sys.SysAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysAuthorityService {

    /**
     * 权限mapper
     */
    private final SysAuthorityMapper authorityMapper;

    /**
     * jetcache管理器
     */
    private final CacheManager cacheManager;
    /**
     * 权限缓存
     */
    private Cache<String, List<SysAuthority>> authorityCache;

    /**
     * 构造函数
     * @param sysAuthorityMapper
     * @param cacheManager
     */
    @Autowired
    private SysAuthorityService(SysAuthorityMapper sysAuthorityMapper, CacheManager cacheManager){
        this.authorityMapper = sysAuthorityMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * 初始化执行
     */
    @PostConstruct
    public void init() {
        QuickConfig qc = QuickConfig.newBuilder("authority")
                .expire(Duration.ofSeconds(86400))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        authorityCache = cacheManager.getOrCreateCache(qc);
        authorityCache.config().setLoader(key -> authorityMapper.list());
    }

    public int insert(SysAuthority authority){
        return 0;
    }

    public int update(SysAuthority authority){
        return 0;
    }

    public int delete(Long id){
        return 0;
    }

    /**
     * 获取所有权限（目录、菜单、按钮、接口）
     * @return
     */
    public Set<SysAuthority> listAll(){
        if(CollectionUtil.isEmpty(authorityCache.get("authority"))){
            authorityCache.put("authority",authorityMapper.list());
        }
        return new HashSet<>(authorityCache.get("authority"));
    }

    public Set<SysAuthority> getAuthorityByIds(Set<Long> ids){
        return listAll().stream().filter(t-> !CollectionUtil.isEmpty(ids) && ids.contains(t.getId())).collect(Collectors.toSet());
    }

    /**
     * 获取所有目录菜单按钮
     * @return
     */
    public List<SysAuthority> getMenu(){
        return authorityCache.get("authority").stream().filter(t->t.getAuthorityType() != 3).toList();
    }

    /**
     * 获取资源清单
     * @return
     */
    public Map<String,String> getResources(){
        return listAll().stream().filter(t-> StrUtil.isNotBlank(t.getBgUri()) && StrUtil.isNotBlank(t.getAuthorityValue())).collect(Collectors.toMap(SysAuthority::getAuthorityValue,SysAuthority::getBgUri));
    }
}
