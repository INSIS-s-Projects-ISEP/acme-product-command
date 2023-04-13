package com.isep.acme.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "SKU is a mandatory attribute of Product.")
    @Size(min = 12, max = 12, message = "SKU must be 12 characters long.")
    private String sku;

    @Column(nullable = false)
    @NotBlank(message = "Designation is a mandatory attribute of Product.")
    @Size(max = 50, message = "Designation must not be greater than 50 characters.")
    private String designation;
    
    @Column(nullable = false)
    @NotBlank(message = "Description is a mandatory attribute of Product.")
    @Size(max = 1200, message = "Description must not be greater than 1200 characters.")
    private String description;

    public Product(String sku, String designation, String description) {
        this.sku = sku;
        this.description = description;
        this.designation = designation;
    }

    public Product update(Product product){
        BeanUtils.copyProperties(product, this, "productId", "sku");
        return this;
    }

}
