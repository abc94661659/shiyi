package com.linshiyi.user.controller;


import com.linshiyi.core.entity.vo.UserBriefVO;
import com.linshiyi.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@Hidden
public class InternalUserController {

    private final UserService userService;

    @PostMapping("/briefs")
    public List<UserBriefVO> getUserBriefs(@RequestBody Set<Long> userIds) {
        return userService.getUserBriefsByIds(userIds);
    }
}