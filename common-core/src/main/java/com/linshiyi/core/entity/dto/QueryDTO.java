package com.linshiyi.core.entity.dto;


import lombok.Data;

@Data
public class QueryDTO {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
