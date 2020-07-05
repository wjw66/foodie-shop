package com.wjw.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.wjw.SpringBootApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @author : wjwjava01@163.com
 * @date : 16:23 2020/7/5
 * @description :
 */
@Slf4j
public class BloomFilterTest extends SpringBootApplicationTest {
    @Test
    public void myTest(){
        /**
         * BloomFilter一共四个create方法，不过最终都是走向第四个。看一下每个参数的含义：
         *    funnel：数据类型(一般是调用Funnels工具类中的)
         *    expectedInsertions：期望插入的值的个数
         *    fpp 错误率(默认值为0.03)
         *    strategy 哈希算法(指定某个算法)
         */
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("utf-8")), 10000
        );

        for (int i = 0; i < 10000; i++) {
            //向布隆过滤器中放入数据
            bloomFilter.put(String.valueOf(i));
        }

        //初始化误判数
        int missCount = 0;
        for (int i = 0; i < 10000; i++) {
            //判断这个数据在布隆过滤器中是否存在
            boolean exist = bloomFilter.mightContain("误判" + i);
            //如果误判了
            if (exist){
                missCount ++;
            }
        }

        System.out.println("误判个数为:" + missCount );
        System.out.println("误判率为:" + BigDecimal.valueOf(missCount).divide(new BigDecimal(10000),2,ROUND_HALF_UP));
    }
}
