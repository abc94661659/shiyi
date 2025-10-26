package com.linshiyi.common.config;

import com.linshiyi.common.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 公共模块自动配置类
 * 用于自动加载common-service中的核心组件
 */
@Configuration
// 导入需要自动生效的配置类或组件
@Import({
        DateTimeConfig.class,       // 时间格式化配置
        GlobalExceptionHandler.class // 全局异常处理器
})
public class CommonAutoConfiguration {

    // 可在此处添加其他公共Bean的定义，例如：
    // @Bean
    // public XXX xxx() {
    //     return new XXX();
    // }
}