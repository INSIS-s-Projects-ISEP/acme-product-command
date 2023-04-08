package com.isep.acme.bootstrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.isep.acme.messaging.RabbitmqService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductBootstrapper implements CommandLineRunner {

    @Value("#{rpcProductExchange.name}")
    private final String rpcProductExchange;
    private final RabbitmqService rabbitmqService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\nbbbbbbbbbbbbbbb");
        System.out.println("AAAAAAAAAAAAAAA");
        System.out.println(rabbitmqService.sendRpc(rpcProductExchange));
        System.out.println("AAAAAAAAAAAAAAA");
        System.out.println("bbbbbbbbbbbbbbb\n");
        System.out.println("bbbbbbbbbbbbbbb\n");

    }

}
