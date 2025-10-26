package com.linshiyi.user.controller;

import com.linshiyi.common.common.Result;
import com.linshiyi.user.domain.dto.UserCreateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Tag(name = "用户服务")
public class UserController {
    

    @GetMapping("/hello")
    public Result<String> hello() {
        LocalDateTime  now = LocalDateTime.now();
        return Result.success(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @PostMapping("/createUser")
    public Result<String> createUser(@RequestBody @Valid UserCreateDTO userCreateDto) {

        return Result.success();
    }

}
