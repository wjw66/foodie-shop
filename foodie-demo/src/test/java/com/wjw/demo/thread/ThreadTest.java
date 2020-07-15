package com.wjw.demo.thread;

import com.wjw.demo.ApplicationTests;
import com.wjw.thread.ThreadPoolExecutorUtil;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/15 13:40
 **/
public class ThreadTest extends ApplicationTests {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    @Test
    public void test() {
        ThreadPoolExecutor threadPool = ThreadPoolExecutorUtil.getThreadPool();
        for (int i = 0; i < 100; i++) {
            ATOMIC_LONG.incrementAndGet();
            final int index = i;
            try{
                Thread.sleep(index * 100);
            }catch (Exception e){
                e.printStackTrace();
            }
            threadPool.execute(() -> System.out.println(index));
            threadPool.execute(() -> System.out.println(ATOMIC_LONG));
        }
    }
}
