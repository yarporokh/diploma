package com.nau.shop.service;

import com.nau.shop.dto.WorkerRegisterBody;
import com.nau.shop.model.User;
import com.nau.shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findALlWorkers() {
        return userRepository.findALlWorkers();
    }

    public void save(WorkerRegisterBody body) {
        User user = userRepository.findByEmail(body.getEmail());
        if (user != null) {
            throw new RuntimeException("Робітник з такою електронною поштою зареєстрований вже зареєстрований");
        }

        User newWorker = User.builder()
                .firstname(body.getFirstname())
                .lastname(body.getLastname())
                .email(body.getEmail())
                .password(passwordEncoder.encode(body.getPassword()))
                .role(body.getRole())
                .build();
        userRepository.save(newWorker);
    }
}
