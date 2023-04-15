package com.isep.acme.dto.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMessage {
    private UUID productId;
    private String sku;
    private String designation;
    private String description;
}
