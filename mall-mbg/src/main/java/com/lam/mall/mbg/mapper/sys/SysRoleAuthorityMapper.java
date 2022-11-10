package com.lam.mall.mbg.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.sys.SysRoleAuthority;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysRoleAuthorityMapper extends BaseMapper<SysRoleAuthority> {
    @Insert("<script>" +
            "INSERT INTO sys_role_authority(roleId,authorityId) VALUES" +
            "<foreach collection='roleAuthority' item='item'   separator=','> " +
            "(#{item.roleId},#{item.authorityId})" +
            "</foreach> " +
            "</script>")
    boolean insertBatch(@Param("roleAuthority") List<SysRoleAuthority> roleAuthorityList);

    @Delete("DELETE FROM sys_role_authority WHERE role_id=#{roleId}")
    boolean deleteByRoleId(Long roleId);

    /**
     * 获取角色对应权限
     * @param roleId
     * @return
     */
    @Select("SELECT authority_id FROM sys_role_authority WHERE role_id=#{roleId}")
    Set<Long> getAuthorityIdsByRoleId(Long roleId);

}
