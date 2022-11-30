package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "token")
    private String token;

    @Schema(title = "登录ip地址")
    private String ip;

    @Schema(title = "登录类型")
    private String clientType;

    @Schema(title = "操作系统")
    private String os;

    @Schema(title = "浏览器")
    private String browser;

    @Schema(title = "登录时间")
    private LocalDateTime loginTime;

    @Schema(title = "最近访问时间")
    private LocalDateTime lastAccessTime;

    @Schema(title = "浏览器标识")
    private String useragent;
}
