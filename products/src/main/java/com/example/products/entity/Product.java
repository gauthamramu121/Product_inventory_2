package com.example.products.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "added_on")
    private LocalDate addedOn;

    @Column(name = "image_name")
    private String imageName;
}
