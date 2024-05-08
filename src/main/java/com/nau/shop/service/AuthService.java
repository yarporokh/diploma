package com.nau.shop.service;

import com.nau.shop.dto.RegisterBody;
import com.nau.shop.email.EmailSender;
import com.nau.shop.email.token.ConfirmationToken;
import com.nau.shop.email.token.ConfirmationTokenService;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final ConfirmationTokenService tokenService;
    private final EmailSender emailSender;

    public boolean registerNewUser(RegisterBody registerBody) {
        if (userRepository.findByEmail(registerBody.getEmail()) == null &&
                phoneRepository.findByPhone(registerBody.getPhone()).isEmpty()) {

            User newUser = User.builder()
                    .firstname(registerBody.getFirstname())
                    .lastname(registerBody.getLastname())
                    .email(registerBody.getEmail())
                    .password(passwordEncoder.encode(registerBody.getRegPassword()))
                    .role(Role.USER)
                    .isEnabled(false)
                    .build();

            User user = userRepository.save(newUser);
            Phone phone = Phone.builder()
                    .phone(registerBody.getPhone())
                    .user(user)
                    .build();
            phoneRepository.save(phone);

            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = ConfirmationToken.builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .user(user)
                    .build();
            tokenService.saveToken(confirmationToken);
            emailSender.sender(user.getEmail(), emailSender.confirmationEmail(token));

            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public void confirmToken(String token) {
        User user = tokenService.getToken(token).get().getUser();
        user.setIsEnabled(true);
        tokenService.setConfirmedAt(token);
        userRepository.save(user);
    }
}
