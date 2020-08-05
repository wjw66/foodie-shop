package com.wjw.consumer.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author : wjwjava01@163.com
 * @date : 22:22 2020/8/5
 * @description :
 */
@Slf4j
@Component
public class RabbitReceive {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * correlationData 唯一标识
     * b （ack）是否发送成功
     * s 发送失败的异常信息
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, b, s) -> {
    };

    /**
     * 对外发送消息的方法
     *
     * @param msg        具体消息内容
     * @param properties 对消息的额外附加属性：如过期事件等
     * @throws Exception
     */
    public <T> void send(T msg, Map<String, Object> properties) throws Exception {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message<T> message = MessageBuilder.createMessage(msg, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        //指定业务唯一ID
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        /**
         * 1指定的 exchange
         * 2指定的routingKey
         * 3传入队列的msg
         * 4确认传入后执行的操作
         * 5指定业务的唯一ID
         */
        rabbitTemplate.convertAndSend("exchange-1",
                "springboot-rabbit", message,
                p -> {log.info("to do something{}", message);return null;},
                data);
    }
}
