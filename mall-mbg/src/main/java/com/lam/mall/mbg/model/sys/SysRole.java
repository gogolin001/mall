package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "角色id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "编码")
    private String roleCode;

    @Schema(title = "名称")
    private String roleName;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    @Schema(title = "启用状态：0->禁用；1->启用")
    private Boolean status;

    @Schema(title = "排序")
    private Integer sort;
}
