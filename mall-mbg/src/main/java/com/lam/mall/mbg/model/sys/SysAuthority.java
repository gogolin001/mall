package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表（目录、菜单、按钮、接口、资源）
 */
@Getter
@Setter
@TableName(value = "sys_authority")
public class SysAuthority implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "父级权限id")
    private Long pid;

    @ApiModelProperty(value = "名称")
    @TableField(value = "authority_name")
    private String name;

    @ApiModelProperty(value = "权限值")
    @TableField(value = "authority_value")
    private String value;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Byte type;

    @ApiModelProperty(value = "前端资源路径")
    @TableField(value = "web_uri")
    private String webUri;

    @ApiModelProperty(value = "后台资源路径")
    @TableField(value = "bg_uri")
    private String bgUri;

    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private boolean status;

    @ApiModelProperty(value = "排序")
    private Byte sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;
}
