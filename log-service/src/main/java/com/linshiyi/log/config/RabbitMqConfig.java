package com.linshiyi.log.config;

import com.linshiyi.core.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 日志交换机, 用于接收日志消息
     *
     * @return 日志交换机
     */
    @Bean
    public FanoutExchange logExchange() {
        // 创建交换机, 持久化, 不需要确认消息
        return new FanoutExchange(MqConstant.LOG_EXCHANGE, true, false);
    }

    @Bean
    public Queue logQueue() {
        // 创建队列, 持久化, 不需要确认消息
        return new Queue(MqConstant.LOG_QUEUE, true);
    }

    @Bean
    public Binding bindingLogQueue(Queue logQueue, FanoutExchange logExchange) {
        // 绑定队列到交换机, 使用路由键
        return BindingBuilder.bind(logQueue).to(logExchange);
    }
}
