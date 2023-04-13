package com.isep.acme.bootstrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.message.ProductMessage;
import com.isep.acme.messaging.RabbitmqService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductBootstrapper implements CommandLineRunner {

    @Value("#{rpcProductExchange.name}")
    private final String rpcProductExchange;

    private final ProductRepository productRepository;
    private final RabbitmqService rabbitmqService;
    private final ProductMapper productMapper;

    @Override
    public void run(String... args) throws Exception{

        log.info("Sending RPC Product Request...");
        String response = (String) rabbitmqService.sendRpc(rpcProductExchange);
        
        List<ProductMessage> messages = productMapper.toMessageList(response);
        List<Product> products = productMapper.toEntityList(messages);
        log.info("Received RPC Product Response | Size: " + products.size());

        productRepository.saveAll(products);
        log.info("Products inserted in the database!");
        
    }
}