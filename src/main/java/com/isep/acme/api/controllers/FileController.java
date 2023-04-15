package com.isep.acme.api.controllers;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.isep.acme.domain.model.ProdImage;
import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.ImageRepository;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.service.FileStorageService;
import com.isep.acme.dto.response.UploadFileResponse;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    private final ImageRepository iRepo;

    private final ProductRepository pRepo;


    @GetMapping(value = "/fileid/{id}")
    public ResponseEntity<ProdImage> findById(@PathVariable("id") final Long id){

        final var prodImage = iRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Not Found"));


        return  ResponseEntity.ok().body(prodImage);
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/ID/{productID}")
    public ResponseEntity<Product> findByID(@PathVariable("productID") final UUID productID){
        final var product = pRepo.findById(productID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return ResponseEntity.ok().body(product);
    }




}