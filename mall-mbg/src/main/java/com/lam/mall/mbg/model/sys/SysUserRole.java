package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色表
 */
@Getter
@Setter
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户id")
    private Long userId;

    @Schema(title = "角色id")
    private Long roleId;


}
