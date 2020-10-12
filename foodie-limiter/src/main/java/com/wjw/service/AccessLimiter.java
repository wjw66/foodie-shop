package com.wjw.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/21 16:28
 **/
@Service
@Slf4j
public class AccessLimiter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private RedisScript<Boolean> redisScript;

    /**
     * 限流方法
     *
     * @param key   限流的key
     * @param limit 每秒多少流量
     */
    public void limiterAccess(String key, Integer limit) {

        Boolean accept = stringRedisTemplate
                .execute(
                        // lua脚本
                        redisScript,
                        //lua脚本中的key
                        Lists.newArrayList(key),
                        //lua脚本中的value
                        limit.toString()
                );
        if (Objects.isNull(accept) || !accept) {
            log.info("请求达到最大数量，被阻塞");
            throw new RuntimeException("请求达到最大数量，被阻塞");
        }
    }
}
