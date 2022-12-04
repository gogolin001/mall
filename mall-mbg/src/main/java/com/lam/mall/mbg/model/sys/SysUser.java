package com.lam.mall.mbg.model.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户表
 */
@Getter
@Setter
@TableName(value ="sys_user", autoResultMap = true)
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "微信UnionId")
    private String unionId;

    @Schema(title = "服务号Id")
    private String mpOpenId;

    @Schema(title = "小程序OpenId")
    private String miniOpenId;

    @Schema(title = "昵称")
    private String nickName;

    @Schema(title = "头像")
    private String icon;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "备注信息")
    private String note;

    @Schema(title = "角色Id")
    private String roleIds;

    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    @Schema(title = "帐号启用状态：0->禁用；1->启用")
    private Boolean status;

    @TableLogic
    private Boolean deleted;

    public Set<Long> getRoleIdList(){
        return StrUtil.isBlank(roleIds) ? null : Arrays.stream(roleIds.split(",")).map(Long::valueOf).collect(Collectors.toSet());
    }
}
