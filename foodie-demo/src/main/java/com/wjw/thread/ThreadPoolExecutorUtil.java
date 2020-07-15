package com.wjw.thread;

import com.wjw.config.ThreadConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/15 9:11
 **/
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getThreadPool() {
        ThreadConfig properties = SpringContextHolder.getBean(ThreadConfig.class);
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new TheadFactoryName()
        );
    }
}
