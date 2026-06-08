package com.example.products.requestDTO;

import jakarta.validation.constraints.Min;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateDTO {

    @Min(value = 0,message = "{product.stock.min}")
    private Integer stock;
}
