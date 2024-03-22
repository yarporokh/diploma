package com.nau.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    @GetMapping("products")
    public String adminProductsPage() {
        return "adminProducts";
    }
}
