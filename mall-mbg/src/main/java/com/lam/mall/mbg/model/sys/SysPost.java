package com.lam.mall.mbg.model.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 岗位
 */
@Getter
@Setter
public class SysPost implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 岗位序号 */
    @ApiModelProperty(value = "岗位序号")
    private Long postId;

    /** 岗位编码 */
    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    /** 岗位名称 */
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    /** 岗位排序 */
    @ApiModelProperty(value = "岗位排序")
    private String postSort;

    /** 状态（0正常 1停用） */
    @ApiModelProperty(value = "状态：0=正常,1=停用")
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;
}
