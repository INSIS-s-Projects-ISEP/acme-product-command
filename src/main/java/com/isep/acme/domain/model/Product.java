package com.isep.acme.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.isep.acme.dto.ProductDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(nullable = false, unique = true)
    public String sku;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String description;

    public Product(Long productId, String sku) {
        this.productId = Objects.requireNonNull(productId);
        setSku(sku);
    }

    public Product(Long productId, String sku, String designation, String description) {
        this(productId, sku);
        setDescription(description);
        setDesignation(designation);
    }

    public Product(String sku) {
        setSku(sku);
    }

    public Product(String sku, String designation, String description) {
        this(sku);
        setDescription(description);
        setDesignation(designation);
    }

    public void setSku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU is a mandatory attribute of Product.");
        }
        if (sku.length() != 12) {
            throw new IllegalArgumentException("SKU must be 12 characters long.");
        }

        this.sku = sku;
    }

    public void setDesignation(String designation) {
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation is a mandatory attribute of Product.");
        }
        if (designation.length() > 50) {
            throw new IllegalArgumentException("Designation must not be greater than 50 characters.");
        }
        this.designation = designation;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is a mandatory attribute of Product.");
        }

        if (description.length() > 1200) {
            throw new IllegalArgumentException("Description must not be greater than 1200 characters.");
        }

        this.description = description;
    }

    public void updateProduct(Product p) {
        setDesignation(p.designation);
        setDescription(p.description);
    }

    public ProductDTO toDto() {
        return new ProductDTO(this.sku, this.designation);
    }

}
