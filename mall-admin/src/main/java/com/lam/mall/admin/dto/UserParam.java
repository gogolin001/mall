package com.lam.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserParam {
    @NotEmpty
    @Schema(title = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotEmpty
    @Schema(title = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(title = "用户头像")
    private String icon;
    @Email
    @Schema(title = "邮箱")
    private String email;
    @Schema(title = "用户昵称")
    private String nickName;
    @Schema(title = "备注")
    private String note;
    @Schema(title = "角色id")
    private String roleIds;
}
