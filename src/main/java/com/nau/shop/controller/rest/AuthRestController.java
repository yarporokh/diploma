package com.nau.shop.controller.rest;

import com.nau.shop.dto.RegisterBody;
import com.nau.shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// TODO: delete/edit

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Boolean> saveNewUser(@RequestBody RegisterBody registerBody) {
        boolean isNew = authService.registerNewUser(registerBody);
        return ResponseEntity.ok(isNew);
    }
}
