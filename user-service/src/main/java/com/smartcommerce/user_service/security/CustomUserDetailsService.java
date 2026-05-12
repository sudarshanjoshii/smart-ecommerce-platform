package com.smartcommerce.user_service.security;

import com.smartcommerce.user_service.entity.User;
import com.smartcommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseGet(() ->
                        userRepository.findByPhone(username)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("User not found"))
                );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail() != null
                        ? user.getEmail()
                        : user.getPhone(),

                user.getPassword(),

                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole().name()
                        )
                )
        );
    }
}