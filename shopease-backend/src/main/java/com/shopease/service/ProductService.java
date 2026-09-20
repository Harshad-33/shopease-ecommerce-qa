package com.shopease.service;

import com.shopease.dto.ProductDto.ProductRequest;
import com.shopease.dto.ProductDto.ProductResponse;
import com.shopease.entity.Category;
import com.shopease.entity.Product;
import com.shopease.exception.ResourceNotFoundException;
import com.shopease.repository.CategoryRepository;
import com.shopease.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductResponse> getProducts(String search, Long categoryId, String sortBy, Boolean inStockOnly) {
        log.info("Fetching products - search: '{}', categoryId: {}, sortBy: {}, inStockOnly: {}", search, categoryId, sortBy, inStockOnly);

        List<Product> products;

        boolean hasSearch = search != null && !search.trim().isEmpty();
        boolean hasCategory = categoryId != null && categoryId > 0;

        if (hasSearch && hasCategory) {
            products = productRepository.searchProductsByCategory(categoryId, search.trim());
        } else if (hasSearch) {
            products = productRepository.searchProducts(search.trim());
        } else if (hasCategory) {
            products = productRepository.findByCategoryIdAndActiveTrue(categoryId);
        } else {
            products = productRepository.findByActiveTrue();
        }

        // Filter in-stock only if requested
        if (Boolean.TRUE.equals(inStockOnly)) {
            products = products.stream()
                    .filter(p -> p.getStockQuantity() > 0)
                    .collect(Collectors.toList());
        }

        // Sorting
        if ("price_asc".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if ("price_desc".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else if ("newest".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        }

        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProductsForAdmin() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Admin creating product: {}", request.getName());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + request.getCategoryId()));

        Product product = new Product(
                request.getName().trim(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getImageUrl(),
                category,
                request.getActive() != null ? request.getActive() : true
        );

        Product saved = productRepository.save(product);
        log.info("Product created with ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Admin updating product ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + request.getCategoryId()));

        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Admin deleting product ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        // Soft delete for integrity with past orders
        product.setActive(false);
        productRepository.save(product);
    }

    public ProductResponse mapToResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.getActive());
        dto.setCreatedAt(product.getCreatedAt());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }
}
