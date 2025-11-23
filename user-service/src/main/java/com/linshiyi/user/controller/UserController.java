package com.linshiyi.user.controller;


import com.linshiyi.core.entity.Result;
import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.dto.UserLoginDTO;
import com.linshiyi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户服务")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public Result<String> register(@RequestBody @Valid UserCreateDTO userCreateDto) {
        userService.register(userCreateDto);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        return Result.success("登录成功", token);
    }
}
