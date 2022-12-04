package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限表（目录、菜单、按钮、接口、资源）
 */
@Getter
@Setter
@TableName(value = "sys_authority")
public class SysAuthority implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "权限id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "父级权限id")
    private Long pid;

    @Schema(title = "所属客户端")
    private String clientType;

    @Schema(title = "权限分类")
    private String authorityClassify;

    @Schema(title = "名称")
    private String authorityName;

    @Schema(title = "权限值")
    private String authorityValue;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）;3->纯接口")
    private Byte authorityType;

    @Schema(title = "前端资源路径")
    private String webUri;

    @Schema(title = "后台资源路径")
    private String bgUri;

    @Schema(title = "启用状态；0->禁用；1->启用")
    private Boolean status;

    @Schema(title = "排序")
    private Byte sort;
}
