package com.linshiyi.core.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Long total;       // 总条数
    private List<T> list;     // 当前页数据
    private Integer pageNum;  // 当前页码
    private Integer pageSize; // 每页条数

    public PageResult() {
    }

    public PageResult(Long total, List<T> list, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}