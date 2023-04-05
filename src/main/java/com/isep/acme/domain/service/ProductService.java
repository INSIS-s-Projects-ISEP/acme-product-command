package com.isep.acme.domain.service;

import java.util.Optional;

import com.isep.acme.domain.model.Product;
import com.isep.acme.dto.ProductDTO;

public interface ProductService {

    ProductDTO create(final Product manager);

    Optional<ProductDTO> findBySku(final String sku);

    ProductDTO updateBySku(final String sku, final Product product);

    void deleteBySku(final String sku);
}
