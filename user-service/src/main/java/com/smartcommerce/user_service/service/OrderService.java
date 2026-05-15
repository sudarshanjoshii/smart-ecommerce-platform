package com.smartcommerce.user_service.service;

import com.smartcommerce.user_service.dto.OrderRequest;
import com.smartcommerce.user_service.entity.Order;
import com.smartcommerce.user_service.entity.Product;
import com.smartcommerce.user_service.exception.InsufficientStockException;
import com.smartcommerce.user_service.exception.ResourceNotFoundException;
import com.smartcommerce.user_service.kafka.OrderProducer;
import com.smartcommerce.user_service.repository.OrderRepository;
import com.smartcommerce.user_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public String placeOrder(OrderRequest request) {

        String user =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        // Check stock
        if (product.getStock() < request.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock available"
            );
        }

        // Reduce stock
        product.setStock(
                product.getStock() - request.getQuantity()
        );

        productRepository.save(product);

        double totalPrice =
                product.getPrice() * request.getQuantity();

        Order order = Order.builder()
                .productId(product.getId())
                .productName(product.getName())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .userEmail(user)
                .build();

        orderRepository.save(order);

        orderProducer.sendOrderEvent(
                "ORDER PLACED | User: " + user +
                        " | Product: " + product.getName() +
                        " | Quantity: " + request.getQuantity()
        );

        return "Order placed successfully";
    }
}