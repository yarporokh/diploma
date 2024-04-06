package com.nau.shop.repository;

import com.nau.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrdersByUserEmailOrderByCreatedDateDesc(String email);

    Order findOrderById(UUID id);
}
