package com.linshiyi.user.service;

import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.dto.UserLoginDTO;

public interface UserService {
    void register(UserCreateDTO userCreateDto);

    String login(UserLoginDTO userLoginDTO);
}
