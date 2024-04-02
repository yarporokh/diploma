package com.nau.shop.controller.rest;

import com.nau.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("add/{id}")
    public void addToOrder(@PathVariable("id") Long id) {
        orderService.addToOrder(id);
    }

    @GetMapping("remove/{id}")
    public void removeFromOrder(@PathVariable("id") Long id) {
        orderService.removeFromOrder(id);
    }
}
