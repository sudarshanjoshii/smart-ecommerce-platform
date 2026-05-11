package com.smartcommerce.user_service.service;

import com.smartcommerce.user_service.dto.RegisterRequest;
import com.smartcommerce.user_service.entity.Role;
import com.smartcommerce.user_service.entity.User;
import com.smartcommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public String Register(RegisterRequest request){
        // At least email or phone required
        if ((request.getEmail() == null || request.getEmail().isBlank()) &&
                (request.getPhone() == null || request.getPhone().isBlank())) {

            throw new RuntimeException("Either email or phone must be provided");
        }
        // Duplicate email check
        if (request.getEmail() != null &&
                userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        // Duplicate phone check
        if (request.getPhone() != null &&
                userRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}
