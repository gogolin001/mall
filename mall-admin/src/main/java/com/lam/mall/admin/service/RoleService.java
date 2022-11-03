package com.lam.mall.admin.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lam.mall.mbg.mapper.sys.SysRoleAuthorityMapper;
import com.lam.mall.mbg.mapper.sys.SysRoleMapper;
import com.lam.mall.mbg.model.sys.SysAuthority;
import com.lam.mall.mbg.model.sys.SysRole;
import com.lam.mall.mbg.model.sys.SysRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final SysRoleMapper roleMapper;

    private final SysRoleAuthorityMapper roleAuthorityMapper;

    @Autowired
    public RoleService(SysRoleMapper roleMapper, SysRoleAuthorityMapper roleAuthorityMapper){
        this.roleMapper = roleMapper;
        this.roleAuthorityMapper = roleAuthorityMapper;
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

    /**
     * 获取所有角色列表
     */
    public List<SysRole> list(){
        roleMapper.selectList(new QueryWrapper());
        return null;
    }

    /**
     * 分页获取角色列表
     */
    public IPage<SysRole> list(String keyword, Integer pageSize, Integer pageNum){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(keyword),"name", keyword);
        return roleMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
    }

    /**
     * 根据管理员ID获取对应菜单
     */
    public List<SysAuthority> getMenuList(Long adminId){
        return null;
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
    public List<SysAuthority> listResource(Long roleId){
        return null;
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
