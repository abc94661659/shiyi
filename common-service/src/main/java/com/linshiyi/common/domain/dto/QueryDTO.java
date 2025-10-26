package com.linshiyi.common.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueryDTO {
    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;
}
