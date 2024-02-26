package com.nau.shop.controller.rest;

import com.nau.shop.model.RegisterBody;
import com.nau.shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;
    @PostMapping("register")
    public void saveNewUser(@RequestBody RegisterBody registerBody) {
        authService.registerNewUser(registerBody);
    }
}
