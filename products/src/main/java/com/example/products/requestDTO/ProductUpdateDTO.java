package com.example.products.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateDTO {

    private String productName;

    private String brand;

    private String description;

    @Min(value = 0 , message = "{product.price.min}")
    private Double price;

}
