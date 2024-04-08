package com.nau.shop.repository;

import com.nau.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrdersByUserEmailOrderByCreatedDateDesc(String email);

    @Query("SELECT o FROM Order o ORDER BY o.createdDate DESC")
    List<Order> findOrdersOrderByCreatedDateDesc();
    Order findOrderById(UUID id);

    @Query("SELECT o FROM Order o WHERE o.user.email LIKE concat('%', :filter, '%') ORDER BY o.createdDate DESC")
    List<Order> findOrdersByFilterUser(String filter);

    @Query("SELECT o FROM Order o WHERE o.manager.email LIKE concat('%', :filter, '%') ORDER BY o.createdDate DESC")
    List<Order> findOrdersByFilterManager(String filter);

}
