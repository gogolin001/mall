package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String roleName;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
    private Boolean status;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
