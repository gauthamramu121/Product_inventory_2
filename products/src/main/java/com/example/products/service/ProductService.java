package com.example.products.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.products.entity.Product;
import com.example.products.entity.Status;
import com.example.products.exceptions.customExceptions.ProductNotFoundException;
import com.example.products.mapper.ProductMapper;
import com.example.products.repository.ProductRepository;
import com.example.products.requestDTO.PaginationRequestDTO;
import com.example.products.requestDTO.ProductRequestDTO;
import com.example.products.requestDTO.ProductUpdateDTO;
import com.example.products.requestDTO.StockUpdateDTO;
import com.example.products.responseDTO.ProductResponseDTO;
import com.example.products.specification.ProductSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final ObjectMapper objectMapper;

    private String saveImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalName = file.getOriginalFilename();

        String extension = originalName.substring(originalName.lastIndexOf("."));

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }

    @Transactional
    public ProductResponseDTO addProduct(String productJson, MultipartFile file) throws IOException {
        log.info("Adding new product");

        ProductRequestDTO requestDTO = objectMapper.readValue(productJson, ProductRequestDTO.class);

        Product product = mapper.convertToEntity(requestDTO);

        product.setImageName(saveImage(file));

        if (requestDTO.getStock() <= 0) {
            product.setStatus(Status.UNAVAILABLE);
        } else {
            product.setStatus(Status.AVAILABLE);
        }

        repository.save(product);

        return mapper.convertToDTO(product);
    }

    public Page<ProductResponseDTO> getProducts(PaginationRequestDTO requestDTO) {
        log.info("Fetching products");

        Specification<Product> specification = ProductSpecification.search(requestDTO);

        PageRequest pageable;

        if (requestDTO.getSortBy() == null || requestDTO.getSortDir() == null) {
            pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize());
        } else {
            pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(), Sort.by(Sort.Direction.fromString(requestDTO.getSortDir()), requestDTO.getSortBy()));
        }

        return repository.findAll(specification, pageable).map(mapper::convertToDTO);
    }

    public List<ProductResponseDTO> getAllProducts() {
        log.info("Fetching all products");
        return repository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .toList();
    }

    public Product findByID(Long id) {
        log.info("Finding products having id:{}", id);

        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product having ID:" + id + " not present in the inventory"));
    }

    public ProductResponseDTO searchProducts(Long id) {

        Product product = findByID(id);

        return mapper.convertToDTO(product);
    }

    @Transactional
    public ProductResponseDTO updateStock(Long id, StockUpdateDTO update) {
        log.info("Fetching products to update stock");

        Product product = findByID(id);

        int newStock = product.getStock() + update.getStock();

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setStock(newStock);

        if (product.getStock() <= 0) {
            product.setStatus(Status.UNAVAILABLE);
        } else {
            product.setStatus(Status.AVAILABLE);
        }

        product.setAddedOn(LocalDate.now());

        repository.save(product);

        return mapper.convertToDTO(product);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, String productJson, MultipartFile file) throws IOException {

        Product product = findByID(id);

        ProductUpdateDTO updateDTO =
                objectMapper.readValue(productJson, ProductUpdateDTO.class);

        mapper.updateMapper(product, updateDTO);

        if (file != null && !file.isEmpty()) {

            if (product.getImageName() != null) {

                Path oldImagePath = Paths.get("uploads")
                        .resolve(product.getImageName());

                Files.deleteIfExists(oldImagePath);
            }

            String newFileName = saveImage(file);

            product.setImageName(newFileName);
        }


        repository.save(product);

        return mapper.convertToDTO(product);
    }

    @Transactional
    public void markUnavailable(Long id) {

        Product product = findByID(id);

        product.setStatus(Status.UNAVAILABLE);
        product.setStock(0);

        repository.save(product);
    }

    public List<Status> getStatus() {
        return List.of(Status.values());
    }
}
