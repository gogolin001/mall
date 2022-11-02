package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "字典id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    /** 字典名称 */
    @ApiModelProperty(name = "字典名称")
    private String dictName;

    /** 字典类型 */
    @ApiModelProperty(name = "字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @ApiModelProperty(name = "状态:0=正常,1=停用")
    private String status;

    @TableLogic
    private boolean deleted;
}
