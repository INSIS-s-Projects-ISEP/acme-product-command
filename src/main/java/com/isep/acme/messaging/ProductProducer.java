package com.isep.acme.messaging;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductProducer {

    private final RabbitmqService rabbitmqService;

    public void productCreated(Product product){
        rabbitmqService.sendMessage("product.product-created", "", product);
    }

}
