package com.nau.shop.service;

import com.nau.shop.dto.AuthBody;
import com.nau.shop.dto.RegisterBody;
import com.nau.shop.model.Phone;
import com.nau.shop.model.Role;
import com.nau.shop.model.User;
import com.nau.shop.repository.PhoneRepository;
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
    private final PhoneRepository phoneRepository;

    public boolean registerNewUser(RegisterBody registerBody) {
        if (userRepository.findByEmail(registerBody.getEmail()).isEmpty() &&
                phoneRepository.findByPhone(registerBody.getPhone()).isEmpty()) {

            User newUser = User.builder()
                    .firstname(registerBody.getFirstname())
                    .lastname(registerBody.getLastname())
                    .email(registerBody.getEmail())
                    .password(passwordEncoder.encode(registerBody.getPassword()))
                    .role(Role.USER)
                    .build();

            User user = userRepository.save(newUser);
            Phone phone = Phone.builder()
                    .phone(registerBody.getPhone())
                    .user(user)
                    .build();
            phoneRepository.save(phone);
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
