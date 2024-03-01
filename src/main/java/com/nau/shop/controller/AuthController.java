package com.nau.shop.controller;

import com.nau.shop.dto.RegisterBody;
import com.nau.shop.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;



    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute("registerBody") RegisterBody registerBody, HttpSession session) {
        Boolean isNew = authService.registerNewUser(registerBody);
        session.setAttribute("isNew", isNew);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String authenticate(
            @RequestParam("logEmail") String email,
            @RequestParam("logPassword") String password
    ) {
        authService.authenticate(email, password);

        return "redirect:/";
    }
}
