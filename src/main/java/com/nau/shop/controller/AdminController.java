package com.nau.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    @GetMapping("products")
    public String adminProductsPage() {
        return "adminProducts";
    }

    @GetMapping("staff")
    public String adminStaffPage() {
        return "adminStaff";
    }

    @GetMapping("orders")
    public String adminOrdersPage() {
        return "adminOrders";
    }

    @GetMapping("user-order/{id}")
    public String adminUserOrderPage(
            @PathVariable("id") UUID id,
            Model model) {
        model.addAttribute("orderId", id);
        return "adminUserOrder";
    }
}
