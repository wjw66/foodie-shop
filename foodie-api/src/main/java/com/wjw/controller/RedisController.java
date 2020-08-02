package com.wjw.controller;

import com.wjw.utils.RedisLock;
import com.wjw.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
@ApiIgnore
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedisOperator redisOperator;

    @GetMapping("/set")
    public Object set(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
        redisOperator.set(key, value);
        return "OK";
    }

    @GetMapping("/get")
    public String get(String key) {
//        return (String)redisTemplate.opsForValue().get(key);
        return redisOperator.get(key);
    }

    @GetMapping("/delete")
    public Object delete(String key) {
//        redisTemplate.delete(key);
        redisOperator.del(key);
        return "OK";
    }

    /**
     * 大量key查询
     * @param keys
     * @return
     */
    @GetMapping("/getALot")
    public Object getALot(String... keys) {
        List<String> resutl = new ArrayList<>();
        for (String k:keys) {
            resutl.add(redisOperator.get(k));
        }
        return resutl;
    }

    /**
     * 批量查询 mget
     * @param keys
     * @return
     */
    @GetMapping("/mget")
    public Object mget(String... keys) {
        List<String> keysList = Arrays.asList(keys);
        return redisOperator.mget(keysList);
    }

    /**
     * 批量查询 pipeline
     * @param keys
     * @return
     */
    @GetMapping("/batchGet")
    public Object batchGet(String... keys) {
        List<String> keysList = Arrays.asList(keys);
        return redisOperator.batchGet(keysList);
    }

    /**
     * 批量查询 pipeline
     * @param
     * @return
     */
    @GetMapping("/lock")
    public String lock() throws InterruptedException {
        boolean lock = redisLock.rLock("order-xxx", 10L);
        if (lock){
            log.info("抢夺锁成功!执行任务！");
            Thread.sleep(5000L);
            boolean unLock = redisLock.unLock("order-xxx");
            if (unLock){
                log.info("释放锁成功！");
            }
        }else {
            log.info("抢夺锁失败!");
        }
        return "分布式锁执行！";
    }

}