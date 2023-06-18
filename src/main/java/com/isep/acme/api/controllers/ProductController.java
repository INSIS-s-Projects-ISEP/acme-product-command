package com.isep.acme.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.request.ProductRequest;
import com.isep.acme.dto.response.ProductResponse;
import com.isep.acme.messaging.ProductProducer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "Product", description = "Endpoints for managing  products")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/products")
class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductProducer productProducer;

    @Operation(summary = "creates a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest productRequest){

        Product product = productMapper.toEntity(productRequest);
        try {
            productService.create(product);
            productProducer.productCreated(product);
            
            ProductResponse productResponse = productMapper.toResponse(product);
            log.info("Product created: " + product.getSku());
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }
        catch(Exception e) {
            log.error("Fail to create product: " + product.getSku());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product must have a unique SKU.");
        }

    }

    @Operation(summary = "updates a product")
    @PatchMapping(value = "/{sku}")
    public ResponseEntity<ProductResponse> update(@PathVariable("sku") String sku, @RequestBody ProductRequest productRequest){
        try {
            Product product = productMapper.toEntity(productRequest);
            Product productUpdated = productService.updateBySku(sku, product);
            productProducer.productUpdated(productUpdated);

            ProductResponse productResponse = productMapper.toResponse(productUpdated);
            log.info("Product updated: " + sku);
            return ResponseEntity.ok().body(productResponse);
            
        } catch(Exception e){
            log.error("Fail to create product: " + sku);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.");
        }
    }
    
    @Operation(summary = "deletes a product")
    @DeleteMapping(value = "/{sku}")
    public ResponseEntity<Product> delete(@PathVariable("sku") String sku ){
        productService.deleteBySku(sku);        
        productProducer.productDeleted(sku);
        log.info("Product deleted: " + sku);
        return ResponseEntity.noContent().build();
    }
}