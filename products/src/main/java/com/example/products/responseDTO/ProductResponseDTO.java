package com.example.products.responseDTO;
 
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.products.entity.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long productId;

    private String productName;

    private String brand;

    private Double price;

    private Integer stock;

    private Status status;

    private LocalDate addedOn;

    private String imageName;
}
