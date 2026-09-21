package com.example.products.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.products.entity.Product;
import com.example.products.requestDTO.StockUpdateDTO;
import com.example.products.responseDTO.ProductResponseDTO;
import com.example.products.service.ProductService;

@Controller
@RequiredArgsConstructor
public class GraphQLController {

    private final ProductService productService;


    @QueryMapping
    public Product getByProductId(@Argument Long id){
        return productService.findByID(id);
    }

    @QueryMapping
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @MutationMapping
    public ProductResponseDTO updateStock(@Argument Long id, @Argument StockUpdateDTO updateDTO){
        return productService.updateStock(id,updateDTO);
    }
}
