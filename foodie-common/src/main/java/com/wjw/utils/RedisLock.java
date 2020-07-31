package com.wjw.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/31 19:39
 **/
public class RedisLock {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private final String value = UUID.randomUUID().toString();

    public boolean rLock(String key,Long time){
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, this.value, time, TimeUnit.SECONDS);
        if (lock){
            return true;
        }
        try {
            Thread.sleep(time);
        }catch (Exception e){
            e.printStackTrace();
        }
       return false;
    }
}
