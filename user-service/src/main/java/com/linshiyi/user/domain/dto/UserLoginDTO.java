package com.linshiyi.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @NotBlank(message = "用户名、邮箱、手机号至少需填写一个")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

}
