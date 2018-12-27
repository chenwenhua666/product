package com.plm.product.message;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : cwh
 * 2018/12/26 0026
 * description ：
 */
@Component
public class OrderReceiver {

    @RabbitListener(bindings = @QueueBinding(
          value = @Queue(value = "order-queue",durable = "true"),
          exchange = @Exchange(name = "order-exchange",durable = "true",type = "topic"),
          key = "order.*"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload String order,
                               @Headers Map<String,Object> heads,
                               Channel channel) throws Exception {
        System.out.println("收到消息--" + order);
        Long deliveryTag = (Long) heads.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
