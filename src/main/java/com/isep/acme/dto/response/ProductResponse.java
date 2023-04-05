package com.isep.acme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ProductResponse {
    public String sku;
    private String designation;
    private String description;
}
