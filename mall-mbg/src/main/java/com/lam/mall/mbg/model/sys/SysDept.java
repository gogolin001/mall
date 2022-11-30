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
 * 部门
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "部门id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "父部门ID")
    private Long pid;

    @Schema(title = "部门编码")
    private String deptCode;

    @Schema(title = "部门名称")
    private String deptName;

    @Schema(title = "负责人")
    private String leader;

    @Schema(title = "联系电话")
    private String phone;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "部门状态:0正常,1停用")
    private String status;

    @Schema(title = "显示顺序")
    private Byte sort;

    @Schema(title = "类型（0代表部门 1代表集团 2代表子公司）")
    private Byte deptType;

    @TableLogic
    private Boolean deleted;
}
