package com.lam.mall.mbg.model.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 字典类型表
 */
@Getter
@Setter
public class SysDictType implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @ApiModelProperty(name = "字典主键")
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
}
