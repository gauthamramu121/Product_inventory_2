package com.example.products.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.example.products.entity.Product;
import com.example.products.entity.Status;
import com.example.products.requestDTO.PaginationRequestDTO;

public class GlobalSearchFilter {

    public static Specification<Product> search(PaginationRequestDTO searchDTO) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            String search = searchDTO.getGlobalSearch();

            if (search != null && !search.isBlank()) {
                String pattern = "%" + searchFilter(search).toLowerCase() + "%";

                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(cb.like(cb.lower(root.get("productName")), pattern));
                orPredicates.add(cb.like(cb.lower(root.get("brand")), pattern));
                orPredicates.add(cb.like(cb.lower(root.get("description")), pattern));

                try {
                    Status status = Status.valueOf(search.toUpperCase());
                    orPredicates.add(cb.equal(root.get("status"), status));
                } catch (IllegalArgumentException ignored) {
                }

                try {
                    Double amount = Double.valueOf(search);
                    orPredicates.add(cb.equal(root.get("price"), amount));
                } catch (NumberFormatException ignored) {
                }

                try {
                    Integer stock = Integer.valueOf(search);
                    orPredicates.add(cb.equal(root.get("stock"), stock));
                } catch (NumberFormatException ignored) {
                }

                predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
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
