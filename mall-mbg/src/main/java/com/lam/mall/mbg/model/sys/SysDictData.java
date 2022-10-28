package com.lam.mall.mbg.model.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 字典数据表
 */
@Getter
@Setter
public class SysDictData  implements Serializable {
    private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "字典id")
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Long dictSort;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    @ApiModelProperty(value = "表格字典样式")
    private String listClass;

    @ApiModelProperty(value = "是否默认:Y=是,N=否")
    private String isDefault;

    @ApiModelProperty(value = "状态:0=正常,1=停用")
    private String status;
}
