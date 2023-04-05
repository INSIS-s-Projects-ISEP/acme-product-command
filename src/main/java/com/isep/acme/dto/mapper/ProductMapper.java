package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.dto.message.ProductMessage;
import com.isep.acme.dto.request.ProductRequest;
import com.isep.acme.dto.response.ProductResponse;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest productRequest){
        return new Product(
            productRequest.getSku(),
            productRequest.getDesignation(),
            productRequest.getDescription()
        );
    }

    public Product toEntity(ProductMessage productMessage){
        return new Product(
            productMessage.getProductId(),
            productMessage.getSku(),
            productMessage.getDesignation(),
            productMessage.getDescription()
        );
    }

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
            product.getSku(),
            product.getDesignation(),
            product.getDescription()
        );
    }

    public ProductMessage toMessage(Product product){
        return new ProductMessage(
            product.getProductId(),
            product.getSku(),
            product.getDesignation(),
            product.getDescription()
        );
    }

}
