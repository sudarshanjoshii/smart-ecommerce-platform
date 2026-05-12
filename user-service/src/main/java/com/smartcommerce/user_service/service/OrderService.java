package com.smartcommerce.user_service.service;

import com.smartcommerce.user_service.dto.OrderRequest;
import com.smartcommerce.user_service.entity.Order;
import com.smartcommerce.user_service.kafka.OrderProducer;
import com.smartcommerce.user_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public String placeOrder(OrderRequest request) {

        String user =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        Order order = Order.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .userEmail(user)
                .build();

        orderRepository.save(order);

        orderProducer.sendOrderEvent(
                "Order placed by: " + user +
                        " Product: " + request.getProductName()
        );

        return "Order placed successfully";
    }
}