package com.linshiyi.core.entity.vo;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageResultVO<T> {
    private Long total;       // 总条数
    private List<T> list;     // 当前页数据
    private Integer pageNum;  // 当前页码
    private Integer pageSize; // 每页条数
    private Integer pages;    // 总页数
    private Integer size;     // 当前页实际条数

    // 构造方法：基于PageHelper的Page对象初始化
    public PageResultVO(Page<T> page) {
        this.total = page.getTotal();
        this.list = page.getResult();
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.pages = page.getPages();
        this.size = page.size();
    }
}