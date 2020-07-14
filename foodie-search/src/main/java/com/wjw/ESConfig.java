package com.wjw;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/14 13:00
 **/
@Configuration
public class ESConfig {

    /**
     * 解决netty引起的issue
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
