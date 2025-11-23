package com.linshiyi.user.service;

import com.linshiyi.core.entity.vo.UserBriefVO;
import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.dto.UserLoginDTO;

import java.util.List;
import java.util.Set;

public interface UserService {
    void register(UserCreateDTO userCreateDto);

    String login(UserLoginDTO userLoginDTO);

    List<UserBriefVO> getUserBriefsByIds(Set<Long> userIds);
}
