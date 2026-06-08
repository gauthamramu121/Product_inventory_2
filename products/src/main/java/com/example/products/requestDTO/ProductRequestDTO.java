package com.example.products.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "{product.name.not_blank}")
    private String productName;

    @NotBlank(message = "{product.brand.not_blank}")
    private String brand;

    @NotNull(message = "{product.price.not_null}")
    @Min(value = 0 , message = "{product.price.min}")
    private Double price;

    @NotNull(message = "{product.stock.not_null}")
    @Min(value = 0,message = "{product.stock.min}")
    private Integer stock;
}
