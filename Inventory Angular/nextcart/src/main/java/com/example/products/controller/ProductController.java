package com.example.products.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.products.entity.Status;
import com.example.products.requestDTO.PaginationRequestDTO;
import com.example.products.requestDTO.ProductUpdateDTO;
import com.example.products.requestDTO.StockUpdateDTO;
import com.example.products.responseDTO.ProductResponseDTO;
import com.example.products.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile file) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productJson, file));
    }

    @PostMapping("/display")
    public ResponseEntity<Page<ProductResponseDTO>> getProducts(@RequestBody PaginationRequestDTO requestDTO) {
        return ResponseEntity.ok(productService.getProducts(requestDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.searchProducts(id));
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<ProductResponseDTO> updateStock(@PathVariable Long id,
            @RequestBody StockUpdateDTO updateDTO) {
        return ResponseEntity.ok(productService.updateStock(id, updateDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
            @RequestParam("product") String productJson,
            @RequestParam(value = "image", required = false)
            MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.updateProduct(id, productJson,file));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.markUnavailable(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<Status>> getStatus(){
        return ResponseEntity.ok(productService.getStatus());
    }
}
