package com.linshiyi.log.mapper;

import com.linshiyi.core.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper {
    void insert(OperationLog operationLog);
}
