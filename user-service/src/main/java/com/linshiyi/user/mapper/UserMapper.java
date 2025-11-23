package com.linshiyi.user.mapper;

import com.linshiyi.core.entity.vo.UserBriefVO;
import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserMapper {
    Map<String, Boolean> checkExists(UserCreateDTO userCreateDto);

    void insert(User user);

    User selectByAccount(String account);

    List<UserBriefVO> selectUserBriefsByIds(Set<Long> userIds);
}
