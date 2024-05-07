package com.nau.shop.service;

import com.nau.shop.dto.WorkerRegisterBody;
import com.nau.shop.model.Phone;
import com.nau.shop.model.User;
import com.nau.shop.dto.UserCheckoutModel;
import com.nau.shop.repository.PhoneRepository;
import com.nau.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;

    public List<User> findALlWorkers() {
        return userRepository.findALlWorkers();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void saveEditedWorker(User worker) {
        User user = userRepository.findByEmail(worker.getEmail());
        user.setFirstname(worker.getFirstname());
        user.setLastname(worker.getLastname());
        user.setEmail(worker.getEmail());
        user.setRole(worker.getRole());
        user.setIsEnabled(worker.getIsEnabled());
        userRepository.save(user);
    }

    public void saveNewWorker(WorkerRegisterBody body) {
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
                .isEnabled(true)
                .build();
        userRepository.save(newWorker);
    }

    public List<User> findALlWorkersWithFilter(String filter) {
        return userRepository.findALlWorkersWithFilter(filter);
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public UserCheckoutModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Phone phone = phoneRepository.findPhoneById(user.getId());
        String phoneNumber = phone != null ? phone.getPhone() : "";

        UserCheckoutModel checkoutModel = UserCheckoutModel.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phone(phoneNumber)
                .build();
        return checkoutModel;
    }
}
