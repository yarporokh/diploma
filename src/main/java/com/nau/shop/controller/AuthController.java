package com.nau.shop.controller;

import com.nau.shop.dto.RegisterBody;
import com.nau.shop.email.token.ConfirmationTokenService;
import com.nau.shop.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute("registerBody") RegisterBody registerBody, HttpSession session) {
        Boolean isNew = authService.registerNewUser(registerBody);
        session.setAttribute("isNew", isNew);
        return "redirect:/";
    }

    @GetMapping("/confirm")
    public String activeAccount(@RequestParam("token") String token) {
        authService.confirmToken(token);
        return "redirect:/";
    }
}
