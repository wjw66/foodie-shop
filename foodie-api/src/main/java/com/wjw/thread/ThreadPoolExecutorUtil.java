package com.wjw.thread;


import com.wjw.config.ThreadConfig;
import org.springframework.beans.factory.annotation.Autowired;

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
//        ThreadConfig properties = SpringContextHolder.getBean(ThreadConfig.class);
        return new ThreadPoolExecutor(
                50,
                50,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                new TheadFactoryName()
        );
    }
}
