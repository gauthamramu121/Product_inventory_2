package com.example.products.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.products.entity.Product;
import com.example.products.requestDTO.ProductRequestDTO;
import com.example.products.requestDTO.ProductUpdateDTO;
import com.example.products.responseDTO.ProductResponseDTO;

@Component
public class ProductMapper {

    public Product convertToEntity(ProductRequestDTO requestDTO){
        return Product.builder()
                .productName(requestDTO.getProductName())
                .brand(requestDTO.getBrand())
                .stock(requestDTO.getStock())
                .price(requestDTO.getPrice())
                .addedOn(LocalDate.now())
                .build();
    }

    public ProductResponseDTO convertToDTO(Product product){
        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .status(product.getStatus())
                .stock(product.getStock())
                .addedOn(product.getAddedOn())
                .imageName(product.getImageName())
                .build();
    }

    public void updateMapper(Product product, ProductUpdateDTO updateDTO) {

        if (updateDTO.getProductName() != null) {
            product.setProductName(updateDTO.getProductName());
        }

        if (updateDTO.getBrand() != null) {
            product.setBrand(updateDTO.getBrand());
        }

        if (updateDTO.getPrice() != null) {
            product.setPrice(updateDTO.getPrice());
        }
    }
}
