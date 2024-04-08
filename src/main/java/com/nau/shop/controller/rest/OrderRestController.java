package com.nau.shop.controller.rest;

import com.nau.shop.model.Order;
import com.nau.shop.model.OrderItem;
import com.nau.shop.model.Receiver;
import com.nau.shop.model.Status;
import com.nau.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping
    private List<OrderItem> getOrder() {
        return orderService.getOrderList();
    }

    @GetMapping("/{id}")
    public OrderItem getItemByProductId(@PathVariable("id") Long id) {
        return orderService.getItemByProductId(id);
    }

    @GetMapping("add/{id}")
    public void addToOrder(@PathVariable("id") Long id) {
        orderService.addToOrder(id);
    }

    @GetMapping("remove/{id}")
    public void removeFromOrder(@PathVariable("id") Long id) {
        orderService.removeFromOrder(id);
    }

    @GetMapping("remove-all/{id}")
    public void removeAllQuantityFromOrder(@PathVariable("id") Long id) {
        orderService.removeAllQuantityFromOrder(id);
    }

    @GetMapping("clear")
    public void clearOrder() {
        orderService.clear();
    }

    @PostMapping("save-order")
    public void saveOrder(@RequestBody Receiver receiver) {
        orderService.saveNewOrder(receiver);
    }

    @GetMapping("user-orders")
    public List<Order> getUserOrders() {
        return orderService.getUserOrders();
    }

    @GetMapping("close-order/{id}")
    public void closeOrder(@PathVariable("id") UUID id) {
        orderService.closeOrder(id);
    }

    @GetMapping("all-orders")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("user-order-info/{id}")
    public Order getUserOrder(@PathVariable("id") UUID id) {
        return orderService.getUserOrder(id);
    }

    @GetMapping("claim-order/{id}")
    public void claimOrder(@PathVariable("id") UUID id) {
        orderService.claimOrder(id);
    }

    @GetMapping("filter/{filter}")
    public List<Order> getOrdersByEmailFilter(@PathVariable("filter") String filter) {
        return orderService.findOrdersByFilterEmail(filter);
    }

    @GetMapping("filter-by-status/{filter}")
    public List<Order> getOrdersByStatusFilter(@PathVariable("filter") String filter) {
        Status status = Status.valueOf(filter);
        return orderService.findOrdersByStatusFilter(status);
    }

    @PostMapping("change-status")
    public void changeOrderStatus(@RequestBody Map<String, String> request) {
        UUID id = UUID.fromString(request.get("id"));
        Status status = Status.valueOf(request.get("status"));
        orderService.changeOrderStatus(id, status);
    }
}
