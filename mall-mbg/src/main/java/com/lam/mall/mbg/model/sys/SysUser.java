package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "微信UnionId")
    private String unionId;

    @ApiModelProperty(value = "服务号Id")
    private String mpOpenId;

    @ApiModelProperty(value = "小程序OpenId")
    private String miniOpenId;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private boolean status;

    @TableLogic
    private boolean deleted;
}
