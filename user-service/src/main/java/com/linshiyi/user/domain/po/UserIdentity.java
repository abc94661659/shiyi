package com.linshiyi.user.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户身份")
public class UserIdentity {
    @Schema(description = "用户身份id")
    private Long id;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "真实姓名")
    private String realName;
    @Schema(description = "身份证号")
    private String idCard;
    @Schema(description = "状态")
    private Integer status;
}
