package com.wjw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/15 12:11
 **/
@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class ThreadConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 存活时间
     */
    private int keepAliveSeconds;

    /**
     * 阻塞队列容量
     */
    private int queueCapacity;
}
