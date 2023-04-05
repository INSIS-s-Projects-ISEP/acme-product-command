package com.isep.acme.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMessage {
    private Long productId;
    private String sku;
    private String designation;
    private String description;
}
