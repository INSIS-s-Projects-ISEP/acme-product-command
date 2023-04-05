package com.isep.acme.messaging;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.message.ProductMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductProducer {

    private final RabbitmqService rabbitmqService;
    private final ProductMapper productMapper;

    public void productCreated(Product product){
        ProductMessage productMessage = productMapper.toMessage(product);
        rabbitmqService.sendMessage("product.product-created", "", productMessage);
    }

}
