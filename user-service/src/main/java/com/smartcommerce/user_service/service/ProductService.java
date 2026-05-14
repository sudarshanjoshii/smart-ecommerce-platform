package com.smartcommerce.user_service.service;

import com.smartcommerce.user_service.dto.ProductRequest;
import com.smartcommerce.user_service.entity.Product;
import com.smartcommerce.user_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // CREATE PRODUCT
    public String createProduct(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        productRepository.save(product);

        return "Product created successfully";
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }
}