package com.lam.mall.mbg.model.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统参数表
 */
@Getter
@Setter
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @ApiModelProperty(value = "参数主键")
    private Long Id;

    /** 参数名称 */
    @ApiModelProperty(value = "参数名称")
    private String configName;

    /** 参数键名 */
    @ApiModelProperty(value = "参数键名")
    private String configKey;

    /** 参数键值 */
    @ApiModelProperty(value = "参数键值")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @ApiModelProperty(value = "系统内置：Y=是,N=否")
    private String configType;
}
