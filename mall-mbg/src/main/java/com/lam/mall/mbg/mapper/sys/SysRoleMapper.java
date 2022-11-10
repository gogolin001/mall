package com.lam.mall.mbg.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.sys.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT * FROM sys_role")
    Set<SysRole> list();

    @Select("SELECT * FROM sys_role WHERE role_name=#{roleName}")
    SysRole getByRoleName(String roleName);
}
