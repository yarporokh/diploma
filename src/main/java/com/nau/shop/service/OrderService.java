package com.nau.shop.service;

import com.nau.shop.model.*;
import com.nau.shop.repository.OrderRepository;
import com.nau.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private List<OrderItem> items = new ArrayList<>();
    private final UserRepository userRepository;

    public void addToOrder(Long id) {
        Optional<OrderItem> existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(id))
                .findFirst();

        if (existingItem.isPresent()) {
            OrderItem item = existingItem.get();
            if (item.getQuantity() < item.getProduct().getQuantity()) {
                item.setQuantity(item.getQuantity() + 1);
            } else {
                throw new RuntimeException("Не вистачає продуктів на складі");
            }
        } else {
            Product product = productService.findById(id);
            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(1)
                    .priceAtOrder(product.getPrice())
                    .build();
            items.add(item);
        }
    }

    public void removeFromOrder(Long id) {
        OrderItem existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(id))
                .findFirst().get();

        if (existingItem.getQuantity() == 1) {
            items.remove(existingItem);
        } else {
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        }
    }

    public List<OrderItem> getOrderList() {
        return items;
    }

    public void removeAllQuantityFromOrder(Long id) {
        OrderItem existingItem = getItemByProductId(id);

        items.remove(existingItem);
    }

    public OrderItem getItemByProductId(Long id) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(id))
                .findFirst().get();
    }

    public void clear() {
        items.clear();
    }

    public void saveNewOrder(Receiver receiver) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AtomicReference<Double> fullPrice = new AtomicReference<>(0.0d);

        items.forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
            fullPrice.updateAndGet(v -> v + item.getQuantity().doubleValue() * item.getPriceAtOrder());
        });

        Order order = Order.builder()
                .receiver(receiver)
                .user((User) authentication.getPrincipal())
                .fullPrice(fullPrice.get())
                .status(Status.NEW)
                .items(items)
                .build();
        orderRepository.save(order);

        items.clear();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return orderRepository.findOrdersByUserEmailOrderByCreatedDateDesc(user.getEmail());
    }

    public void closeOrder(UUID id) {
        Order order = orderRepository.findOrderById(id);
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);

        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productService.save(product);
        });
    }
}
