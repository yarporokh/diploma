package com.nau.shop.service;

import com.nau.shop.model.*;
import com.nau.shop.repository.OrderRepository;
import com.nau.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
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
        Order savedOrder = orderRepository.save(order);
        savedOrder.getItems().forEach(item -> item.setOrder(savedOrder));
        orderRepository.save(savedOrder);
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

    public List<Order> findAll() {
        return orderRepository.findOrdersOrderByCreatedDateDesc();
    }

    public Order getUserOrder(UUID id) {
        return orderRepository.findOrderById(id);
    }

    public void claimOrder(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Order order = orderRepository.findOrderById(id);

        if (order.getManager() != null) {
            throw new RuntimeException("Менеджер вже назначений");
        }

        order.setManager(user);
        order.setStatus(Status.PROCESSING);
        orderRepository.save(order);
    }


    public List<Order> findOrdersByFilterEmail(String filter) {
        List<Order> filteredUserEmail = orderRepository.findOrdersByFilterUser(filter);
        List<Order> filteredManagerEmail = orderRepository.findOrdersByFilterManager(filter);
        return Stream.concat(filteredUserEmail.stream(), filteredManagerEmail.stream()).toList();
    }

    public void changeOrderStatus(UUID id, Status status) {
        Order order = orderRepository.findOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByStatusFilter(Status status) {
        return orderRepository.findOrdersByStatusOrderByCreatedDateDesc(status);
    }
}
