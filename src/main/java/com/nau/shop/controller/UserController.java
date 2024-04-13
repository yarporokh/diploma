package com.nau.shop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cabinet")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    @GetMapping("orders")
    public String getMyOrdersPage() {
        return "myOrders";
    }
}
