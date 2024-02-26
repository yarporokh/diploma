package com.nau.shop.service;

import com.nau.shop.model.AuthBody;
import com.nau.shop.model.RegisterBody;
import com.nau.shop.model.Role;
import com.nau.shop.model.User;
import com.nau.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public boolean registerNewUser(RegisterBody registerBody) {
        if (userRepository.findByEmail(registerBody.getEmail()).isEmpty()) {
            User user = User.builder()
                    .firstname(registerBody.getFirstname())
                    .lastname(registerBody.getLastname())
                    .email(registerBody.getEmail())
                    .password(passwordEncoder.encode(registerBody.getPassword()))
                    .phone(registerBody.getPhone())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void authenticate(AuthBody authBody) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authBody.getEmail(), authBody.getPassword()
                )
        );
    }
}
