package com.smartcommerce.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String email;

    private String phone;

    @NotBlank(message = "Password is required")
    private String password;
}
