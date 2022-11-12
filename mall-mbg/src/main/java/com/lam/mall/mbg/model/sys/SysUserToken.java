package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户token表
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@TableName("sys_user_token")
public class SysUserToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private Long username;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "登录ip地址")
    private String ip;

    @ApiModelProperty(value = "登录类型")
    private String clientType;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "最近访问时间")
    private LocalDateTime lastAccessTime;

    @ApiModelProperty(value = "浏览器标识")
    private String useragent;
}
