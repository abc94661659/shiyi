package com.linshiyi.user.mapper;

import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    Map<String, Boolean> checkExists(UserCreateDTO userCreateDto);

    void insert(User user);

    User selectByAccount(String account);
}
