package com.linshiyi.client.user;

import com.linshiyi.core.entity.vo.UserBriefVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "user-service")
public interface UserClient {
    /**
     * 批量获取用户简要信息（昵称 + 头像）
     */
    @PostMapping("/internal/users/briefs")
    List<UserBriefVO> getUserBriefs(@RequestBody Set<Long> userIds);
}