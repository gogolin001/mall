package com.lam.mall.mbg.maper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.sys.SysRoleAuthority;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRoleAuthorityMapper extends BaseMapper<SysRoleAuthority> {
    @Insert("<script>" +
            "INSERT INTO sys_role_authority(id,roleId,authorityId) VALUES" +
            "<foreach collection='roleAuthority' item='item'   separator=','> " +
            "(#{item.id},#{item.roleId},#{item.authorityId})" +
            "</foreach> " +
            "</script>")
    boolean insertBatch(@Param("roleAuthority") List<SysRoleAuthority> roleAuthorityList);
}
