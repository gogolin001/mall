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
 * 部门
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "父部门ID")
    private Long pid;

    @ApiModelProperty(value = "部门编码")
    private String deptCode;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态:0正常,1停用")
    private String status;

    @ApiModelProperty(value = "显示顺序")
    private Byte sort;

    @ApiModelProperty(value = "类型（0代表部门 1代表集团 2代表子公司）")
    private Byte deptType;

    @TableLogic
    private Boolean deleted;
}
