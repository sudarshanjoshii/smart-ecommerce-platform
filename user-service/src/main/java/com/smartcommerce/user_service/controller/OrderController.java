package com.smartcommerce.user_service.controller;

import com.smartcommerce.user_service.dto.OrderRequest;
import com.smartcommerce.user_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(
            @RequestBody OrderRequest request) {

        return ResponseEntity.ok(
                orderService.placeOrder(request)
        );
    }
}