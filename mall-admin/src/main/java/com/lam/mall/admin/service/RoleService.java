package com.lam.mall.admin.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lam.mall.mbg.mapper.sys.SysRoleAuthorityMapper;
import com.lam.mall.mbg.mapper.sys.SysRoleMapper;
import com.lam.mall.mbg.model.sys.SysAuthority;
import com.lam.mall.mbg.model.sys.SysRole;
import com.lam.mall.mbg.model.sys.SysRoleAuthority;
import com.lam.mall.mbg.model.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final SysRoleMapper roleMapper;

    private final SysRoleAuthorityMapper roleAuthorityMapper;

    private final CacheManager cacheManager;

    private Cache<Long, SysRole> roleCache;

    private Cache<Long, Set<Long>> roleAuthorityCache;

    private final SysAuthorityService authorityService;


    @Autowired
    public RoleService(SysRoleMapper roleMapper, SysRoleAuthorityMapper roleAuthorityMapper, CacheManager cacheManager, SysAuthorityService authorityService){
        this.roleMapper = roleMapper;
        this.roleAuthorityMapper = roleAuthorityMapper;
        this.cacheManager = cacheManager;
        this.authorityService = authorityService;
    }

    @PostConstruct
    public void init() {
        //用户缓存
        QuickConfig qc1 = QuickConfig.newBuilder("role:")
                //.expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        roleCache = cacheManager.getOrCreateCache(qc1);
        roleCache.config().setLoader(roleMapper::selectById);

        //token缓存
        QuickConfig qc2 = QuickConfig.newBuilder("role_authority:")
                //.expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH) // two level cache
                .localLimit(0)
                .syncLocal(true) // invalidate local cache in all jvm process after update
                .build();
        roleAuthorityCache = cacheManager.getOrCreateCache(qc2);
        roleAuthorityCache.config().setLoader(this::getRoleId);
    }

    /**
     * 添加角色
     */
    public int create(SysRole role){
        role.setCreateTime(LocalDateTime.now());
        role.setSort(0);
        return roleMapper.insert(role);
    }

    /**
     * 修改角色信息
     */
    public int update(Long id, SysRole role){
        role.setId(id);
        return roleMapper.updateById(role);
    }

    /**
     * 批量删除角色
     */
    public int delete(List<Long> ids){
        return roleMapper.deleteBatchIds(ids);
    }
    public SysRole getRole(Long id) {
        var user = roleCache.get(id);
        if(ObjectUtil.isNull(user)){
            user = roleMapper.selectById(id);
            if(ObjectUtil.isNull(user)){
                return null;
            }
            roleCache.put(id, user);
        }
        return user;
    }
    /**
     * 获取所有角色列表
     */
    public Set<SysRole> list(){
        return roleMapper.list();
    }

    /**
     * 分页获取角色列表
     */
    public IPage<SysRole> list(String keyword, Integer pageSize, Integer pageNum){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(keyword),"name", keyword);
        return roleMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
    }

    public Set<Long> getRoleId(Long roleId){
        return roleAuthorityMapper.getAuthorityIdsByRoleId(roleId);
    }

    /**
     * 获取角色相关菜单
     */
    public List<SysAuthority> listMenu(Long roleId){
        return null;
    }

    /**
     * 获取角色相关资源
     */
    public Set<String> listResource(Long roleId){
        Set<Long> roleNames = new HashSet<>();
        roleNames.add(roleId);
        return listResource(roleNames);
    }

    public Set<String> listResource(Set<Long> roleIds){
        Set<String> authorityValues= new HashSet<>();
        roleIds.forEach(item -> {
            SysRole role =  getRole(item);
            authorityValues.add("ROLE_"+role.getRoleCode());
            authorityValues.addAll(authorityService.getAuthorityByIds(roleAuthorityCache.get(item)).stream().filter(t->StrUtil.isNotBlank(t.getBgUri())).map(SysAuthority::getAuthorityValue).collect(Collectors.toSet()));
        });
        return authorityValues;
    }

    /**
     * 给角色分配菜单
     */
    @Transactional
    public int allocAuthority(Long roleId, List<Long> authorityIds){
        //先删除原有关系
        QueryWrapper<SysRoleAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", roleId);
        roleAuthorityMapper.delete(queryWrapper);
        List<SysRoleAuthority> ra= new ArrayList<>();
        //批量插入新关系
        for (Long authorityId : authorityIds) {
            ra.add(SysRoleAuthority.builder().roleId(roleId).authorityId(authorityId).build());
        }
        roleAuthorityMapper.insertBatch(ra);
        return authorityIds.size();
    }
}
