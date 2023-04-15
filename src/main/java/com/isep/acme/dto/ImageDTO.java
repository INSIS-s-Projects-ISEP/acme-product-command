package com.isep.acme.dto;

import java.util.UUID;

import com.isep.acme.domain.service.ImageService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    private ImageService service;
    private UUID id;
    private UUID productId;
    
    public ImageDTO(UUID id, UUID productId) {
        this.id = id;
        this.productId = productId;
    }
    
}
