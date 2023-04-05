package com.isep.acme.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ProductMessage {
    private Long productId;
    private String sku;
    private String designation;
    private String description;
}
