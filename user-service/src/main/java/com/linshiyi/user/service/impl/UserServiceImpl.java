package com.linshiyi.user.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.nacos.common.utils.StringUtils;
import com.linshiyi.common.config.properties.JwtProperties;
import com.linshiyi.common.exception.BusinessException;
import com.linshiyi.core.entity.dto.JwtPayloadDTO;
import com.linshiyi.core.enums.StatusCodeEnum;
import com.linshiyi.core.utils.JwtUtil;
import com.linshiyi.user.domain.dto.UserCreateDTO;
import com.linshiyi.user.domain.dto.UserLoginDTO;
import com.linshiyi.user.domain.po.User;
import com.linshiyi.user.mapper.UserMapper;
import com.linshiyi.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final Snowflake snowflake;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    /**
     * 默认注册用户
     *
     * @param userCreateDto 创建注册信息
     */
    @Override
    @Transactional
    public void register(UserCreateDTO userCreateDto) {
        isCreateUser(userCreateDto);

        User user = new User();
        user.setId(snowflake.nextId());
        user.setUserName(userCreateDto.getUserName().trim());
        user.setNickName(userCreateDto.getUserName().trim());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword().trim()));
        user.setEmail(userCreateDto.getEmail().trim());
        user.setPhone(userCreateDto.getPhone().trim());
        // TODO 邮箱验证码
        userMapper.insert(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        String account = userLoginDTO.getAccount().trim();
        String password = userLoginDTO.getPassword().trim();
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "用户名或密码错误");
        }
        if (user.getStatus() == 1) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "账号已被禁用");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "用户名或密码错误");
        }
        // TODO 失败重试次数

        LocalDateTime now = LocalDateTime.now();
        JwtPayloadDTO payload = new JwtPayloadDTO();
        payload.setUserId(user.getId());
        payload.setUserName(user.getUserName());
        payload.setRole("USER"); // 假设默认角色为USER（实际可从数据库查询）
        payload.setIssuedAt(now);
        payload.setExpiration(now.plusMinutes(jwtProperties.getExpireMinutes())); // 过期时间

        // 生成token
        return JwtUtil.generateToken(payload, jwtProperties.getSecret());

    }

    // 是否创建
    private void isCreateUser(UserCreateDTO userCreateDto) {
        userCreateDto.setUserName(userCreateDto.getUserName().trim());
        userCreateDto.setEmail(userCreateDto.getEmail().trim());
        if (userCreateDto.getPhone() != null) {
            userCreateDto.setPhone(userCreateDto.getPhone().trim());
        }
        Map<String, Boolean> existMap = userMapper.checkExists(userCreateDto);
        // 检查用户名是否存在
        if (Boolean.TRUE.equals(existMap.get("userName_exists"))) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "用户名已存在");
        }
        // 检查邮箱是否存在
        if (Boolean.TRUE.equals(existMap.get("email_exists"))) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "邮箱已存在");
        }
        // 检查手机号是否存在
        if (StringUtils.hasText(userCreateDto.getPhone()) && Boolean.TRUE.equals(existMap.get("phone_exists"))) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "手机号已存在");
        }
    }

}
