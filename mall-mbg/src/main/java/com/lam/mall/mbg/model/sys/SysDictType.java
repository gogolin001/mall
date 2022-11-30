package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典分类表
 */
@Getter
@Setter
@TableName("sys_dict_type")
public class SysDictType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Schema(title ="字典id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 字典名称 */
    @Schema(title ="字典名称")
    private String dictName;

    /** 字典类型 */
    @Schema(title ="字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @Schema(title ="状态:0=正常,1=停用")
    private String status;

    @TableLogic
    private Boolean deleted;
}
