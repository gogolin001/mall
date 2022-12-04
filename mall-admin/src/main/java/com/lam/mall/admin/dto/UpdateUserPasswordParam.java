package com.lam.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserPasswordParam {
    @NotEmpty
    @Schema(title = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotEmpty
    @Schema(title = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;
    @NotEmpty
    @Schema(title = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
