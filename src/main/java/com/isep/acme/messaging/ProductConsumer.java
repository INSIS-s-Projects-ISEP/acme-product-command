package com.isep.acme.messaging;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.service.ProductService;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {

    private final String instanceId;
    private final ProductService productService;
    private final MessageConverter messageConverter;
    
    @RabbitListener(queues = "#{productCreatedQueue.name}", ackMode = "MANUAL")
    public void productCreated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            log.info("Received own message.");
            return;
        }

        Product product = (Product) messageConverter.fromMessage(message);
        log.info("Product received: " + product);
        productService.create(product);
        channel.basicAck(tag, false);
        log.info("Product created: " + product);
        
    }
}
