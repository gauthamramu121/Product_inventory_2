package com.example.products.requestDTO;

import jakarta.validation.constraints.Min;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateDTO {

    private String productName;

    private String brand;

    @Min(value = 0 , message = "{product.price.min}")
    private Double price;

}
