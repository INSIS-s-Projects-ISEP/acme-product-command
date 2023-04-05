package com.isep.acme.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    private String sku;
    private String designation;
    private String description;
}
