package com.nau.shop.service;

import com.nau.shop.dto.RegisterBody;
import com.nau.shop.model.Phone;
import com.nau.shop.model.Role;
import com.nau.shop.model.User;
import com.nau.shop.repository.PhoneRepository;
import com.nau.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;

    public boolean registerNewUser(RegisterBody registerBody) {
        if (userRepository.findByEmail(registerBody.getEmail()) == null &&
                phoneRepository.findByPhone(registerBody.getPhone()).isEmpty()) {

            User newUser = User.builder()
                    .firstname(registerBody.getFirstname())
                    .lastname(registerBody.getLastname())
                    .email(registerBody.getEmail())
                    .password(passwordEncoder.encode(registerBody.getRegPassword()))
                    .role(Role.USER)
                    .isEnabled(true)
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
