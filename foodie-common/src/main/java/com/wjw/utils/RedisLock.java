package com.wjw.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/31 19:39
 **/
@Slf4j
@Component
public class RedisLock {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private final String value = UUID.randomUUID().toString();

    public boolean rLock(String key, Long time) {
        boolean locked = false;
        //重试次数
        int tryCount = 3;
        //分布式锁，失败重试
        while (!locked && tryCount > 0) {
            locked = redisTemplate.opsForValue().setIfAbsent(key, this.value, time, TimeUnit.MINUTES);
            tryCount--;
            if (!locked) {
                log.info("抢夺锁失败，等待重试！");
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                log.error("线程被中断" + Thread.currentThread().getId(), e);
            }
        }
        return locked;
    }


    public Boolean unLock(String key) {
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Collections.singletonList(key);

        Boolean result = redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁成功！" + result);
        return result;
    }
}
