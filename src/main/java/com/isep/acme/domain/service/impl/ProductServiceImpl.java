package com.isep.acme.domain.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public ProductDTO create(Product product) {
        return repository.save(product).toDto();
    }

    @Override
    public Optional<ProductDTO> findBySku(String sku) {
        Optional<Product> product = repository.findBySku(sku);

        return product.isPresent() ? Optional.of(product.get().toDto()) : Optional.empty();
    }

    @Override
    public ProductDTO updateBySku(String sku, Product product) {

        Optional<Product> productToUpdate = repository.findBySku(sku);
        if(productToUpdate.isEmpty()){
            return null;
        }

        productToUpdate.get().updateProduct(product);
        Product productUpdated = repository.save(productToUpdate.get());
        return productUpdated.toDto();
    }

    @Override
    public void deleteBySku(String sku) {
        repository.deleteBySku(sku);
    }

}
