package com.example.products.requestDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.products.entity.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequestDTO {

    private Integer page;

    private Integer size;

    private Long productId;

    private String productName;

    private String brand;

    private Double price;

    private Integer stock;

    private Status status;

    private LocalDate addedOn;

    private String sortBy;

    private String sortDir;

}
