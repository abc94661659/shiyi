package com.linshiyi.log.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linshiyi.core.constant.MqConstant;
import com.linshiyi.core.entity.OperationLog;
import com.linshiyi.log.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogConsumer {
    private final OperationLogMapper operationLogMapper;
    private final ObjectMapper objectMapper;

    /**
     * 监听日志队列，接收并存储日志
     *
     * @param logJson 接收到的 JSON 格式的日志字符串
     */
    @RabbitListener(queues = MqConstant.LOG_QUEUE)
    public void receiveLog(String logJson) {
        try {
            OperationLog operationLog = objectMapper.readValue(logJson, OperationLog.class);
            operationLogMapper.insert(operationLog);
            log.info("保存日志成功: {}", logJson);
        } catch (Exception e) {
            log.error("保存日志失败: {}", logJson, e);
        }
    }
}
