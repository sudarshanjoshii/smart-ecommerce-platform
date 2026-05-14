package com.smartcommerce.user_service.controller;

import com.smartcommerce.user_service.dto.ProductRequest;
import com.smartcommerce.user_service.entity.Product;
import com.smartcommerce.user_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public ResponseEntity<String> createProduct(
            @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.createProduct(request)
        );
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }
}