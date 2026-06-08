package com.example.products.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.example.products.entity.Product;
import com.example.products.requestDTO.PaginationRequestDTO;

public class ProductSpecification {

    public static Specification<Product> search(PaginationRequestDTO requestDTO){
        return(root, query, cb) ->{

            List<Predicate> predicates = new ArrayList<>();

            if (requestDTO.getProductId() != null){
                predicates.add(cb.equal(root.get("productId"),requestDTO.getProductId()));
            }

            if (requestDTO.getProductName() != null && !requestDTO.getProductName().isBlank()) {
                String filter = searchFilter(requestDTO.getProductName());
                String pattern = "%" + filter.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("productName")), pattern));
            }

            if (requestDTO.getBrand() != null && !requestDTO.getBrand().isBlank()) {
                String filter = searchFilter(requestDTO.getBrand());
                String pattern = "%" + filter.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("brand")), pattern));
            }

            if (requestDTO.getPrice() != null){
                predicates.add(cb.equal(root.get("price"),requestDTO.getPrice()));
            }

            if (requestDTO.getStock() != null){
                predicates.add(cb.equal(root.get("stock"),requestDTO.getStock()));
            }

            if (requestDTO.getStatus() != null){
                predicates.add(cb.equal(root.get("status"),requestDTO.getStatus()));
            }

            if (requestDTO.getAddedOn() != null){
                predicates.add(cb.equal(root.get("addedOn"),requestDTO.getAddedOn()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static String searchFilter(String input) {
        return input.replace("//", "////")
                .replace("%", "//%")
                .replace("_", "//_");
    }
}
