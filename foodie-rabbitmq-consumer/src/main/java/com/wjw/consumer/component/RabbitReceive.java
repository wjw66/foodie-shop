package com.wjw.consumer.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @author : wjwjava01@163.com
 * @date : 22:22 2020/8/5
 * @description :
 */
@Slf4j
@Component
public class RabbitReceive {

    /**
     * 建立mq的绑定关系,写死在注解里了,后续建议写在配置文件中
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1", durable = "true"),
            exchange = @Exchange(name = "exchange-1", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "springboot.*"))
    @RabbitHandler
    public <T> void onMessage(Message<T> message, Channel channel) throws IOException {
        //1.消费消息,message.getPayload()获取消息体
        log.info("消费消息:{}",message.getPayload());

        //2.处理成功后获取deliveryTag 并进行手工的ACK操作
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        if (Objects.isNull(deliveryTag)) {
            return;
        }
        //delivery_tag是消息投递序号，每个channel对应一个(long类型),true批量,false不批量
        channel.basicAck(deliveryTag,false);
    }
}
