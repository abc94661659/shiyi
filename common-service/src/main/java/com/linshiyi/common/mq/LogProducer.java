package com.linshiyi.common.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linshiyi.core.constant.MqConstant;
import com.linshiyi.core.entity.OperationLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 发送日志消息
     *
     * @param operationLog 日志对象
     */
    public void sendLog(OperationLog operationLog) {
        try {
            String logJson = objectMapper.writeValueAsString(operationLog);
            MessagePostProcessor postProcessor = message -> {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            };
            rabbitTemplate.convertAndSend(MqConstant.LOG_EXCHANGE, MqConstant.LOG_ROUTING_KEY, logJson, postProcessor);
            log.info("发送日志成功：{}", logJson);
        } catch (JsonProcessingException e) {
            log.error("发送日志失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
