package com.isep.acme.domain.service;

import com.isep.acme.domain.model.Product;

public interface ProductService {

    Product create(Product product);

    Product updateBySku(String sku, Product product);

    void deleteBySku(String sku);
}
