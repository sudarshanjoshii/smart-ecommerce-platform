package com.smartcommerce.user_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {

    private UUID productId;

    private Integer quantity;
}