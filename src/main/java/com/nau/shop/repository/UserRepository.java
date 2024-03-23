package com.nau.shop.repository;

import com.nau.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN' or u.role = 'MANAGER' ORDER BY id DESC")
    List<User> findALlWorkers();
}
