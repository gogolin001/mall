package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色权限表
 */
@Getter
@Setter
@Builder
@TableName("sys_role_authority")
public class SysRoleAuthority implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "角色id")
    private Long roleId;

    @Schema(title = "权限id")
    private Long authorityId;
}
