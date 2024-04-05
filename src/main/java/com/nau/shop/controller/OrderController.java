package com.nau.shop.controller;

import com.nau.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("checkout")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String checkoutPage() {
        return "checkoutPage";
    }

    @PostMapping
    public String confirmOder() {
        orderService.clear();
        return "redirect:/";
    }
}