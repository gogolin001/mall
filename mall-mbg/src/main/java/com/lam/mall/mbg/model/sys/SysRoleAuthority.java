package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色权限表
 */
@Getter
@Setter
public class SysRoleAuthority implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    private Long roleId;

    private Long authorityId;
}
