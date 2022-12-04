package com.lam.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginParam
{
    @NotEmpty(message = "用户名不能为空")
    @Schema(title ="用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Schema(title = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
