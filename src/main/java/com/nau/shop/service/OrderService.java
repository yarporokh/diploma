package com.nau.shop.service;

import com.nau.shop.model.*;
import com.nau.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private List<OrderItem> items = new ArrayList<>();

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

    public void save(Receiver receiver) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Order order = Order.builder()
                .receiver(receiver)
                .user((User) authentication.getPrincipal())
                .items(items)
                .build();
        orderRepository.save(order);


        //TODO: replace to admin\manager logic
        items.forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
        });
        items.clear();
    }
}
