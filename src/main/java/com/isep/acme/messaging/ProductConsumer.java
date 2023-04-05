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
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.message.ProductMessage;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {

    private final MessageConverter messageConverter;
    private final String instanceId;
    
    private final ProductService productService;
    private final ProductMapper productMapper;
    
    @RabbitListener(queues = "#{productCreatedQueue.name}", ackMode = "MANUAL")
    public void productCreated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }

        ProductMessage productMessage = (ProductMessage) messageConverter.fromMessage(message);
        log.info("Product received: " + productMessage);

        Product product = productMapper.toEntity(productMessage);
        productService.create(product);
        channel.basicAck(tag, false);
        
        log.info("Product created: " + product);
    }

    @RabbitListener(queues = "#{productUpdatedQueue.name}", ackMode = "MANUAL")
    public void productUpdated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }

        ProductMessage productMessage = (ProductMessage) messageConverter.fromMessage(message);
        log.info("Product received: " + productMessage);

        Product product = productMapper.toEntity(productMessage);
        productService.updateBySku(product.getSku(),product);
        channel.basicAck(tag, false);
        
        log.info("Product updated: " + product);
    }

    @RabbitListener(queues = "#{productDeletedQueue.name}", ackMode = "MANUAL")
    public void productDeleted(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }

        String sku = (String) messageConverter.fromMessage(message);
        log.info("Product received: " + sku);
        productService.deleteBySku(sku);
        channel.basicAck(tag, false);
        
        log.info("Product deleted: " + sku);
    }
}
