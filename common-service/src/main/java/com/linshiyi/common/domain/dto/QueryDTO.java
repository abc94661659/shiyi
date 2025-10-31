package com.linshiyi.common.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryDTO {
    /**
     * 页码
     */
    @Schema(description = "页码",defaultValue = "1")
    private Integer pageNum = 1;
    /**
     * 每页条数
     */
    @Schema(description = "每页条数",defaultValue = "10")
    private Integer pageSize = 10;
}
