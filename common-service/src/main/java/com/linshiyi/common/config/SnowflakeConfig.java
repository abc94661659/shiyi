package com.linshiyi.common.config;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    // 数据中心ID（可通过配置文件指定，默认0）
    @Value("${snowflake.data-center-id:0}")
    private long dataCenterId;

    // 机器ID（可通过配置文件指定，默认取本机IP后两位）
    @Value("${snowflake.machine-id:${random.int[0,31]}}") // 若未配置，随机0-31
    private long machineId;

    @Bean
    public Snowflake snowflake() {
        // 若机器ID未配置，自动获取本机IP后两位作为机器ID（确保分布式环境唯一）
        if (machineId == 0) {
            machineId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) % 32;
        }
        return IdUtil.getSnowflake(dataCenterId, machineId);
    }
}