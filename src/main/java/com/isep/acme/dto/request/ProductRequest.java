package com.isep.acme.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    public String sku;
    private String designation;
    private String description;
}
