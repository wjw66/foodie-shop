package com.wjw.thread;

import com.wjw.SpringBootApplicationTest;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/15 13:40
 **/
public class ThreadTest extends SpringBootApplicationTest {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    @Test
    public void test() {
        ThreadPoolExecutor pool1 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool2 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool3 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool4 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool5 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool6 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool7 = ThreadPoolExecutorUtil.getThreadPool();
        ThreadPoolExecutor pool8 = ThreadPoolExecutorUtil.getThreadPool();
        long start = System.currentTimeMillis();
        while (ATOMIC_LONG.get() < 100) {
            try {
                Thread.sleep(ATOMIC_LONG.get() * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }

            pool1.execute(() -> {
                System.out.println(Thread.currentThread());
                System.out.println(ATOMIC_LONG.incrementAndGet());

            });
            pool2.execute(() -> {
                System.out.println(Thread.currentThread());
                System.out.println(ATOMIC_LONG.incrementAndGet());
            });


        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        // 9 8210
        // 4 12036
        //6526
        //6502
        // 1 49304
    }

}
