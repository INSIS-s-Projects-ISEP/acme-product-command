package com.isep.acme.domain.service.impl;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateBySku(String sku, Product product) {

        Product productToUpdate = productRepository.findBySku(sku).orElseThrow();
        productToUpdate.update(product);

        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteBySku(String sku) {
        productRepository.deleteBySku(sku);
    }

}
