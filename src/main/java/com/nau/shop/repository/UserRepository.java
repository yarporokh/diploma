package com.nau.shop.repository;

import com.nau.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN' or u.role = 'MANAGER' ORDER BY u.id DESC")
    List<User> findALlWorkers();

    @Query("SELECT u FROM User u WHERE (u.role = 'ADMIN' or u.role = 'MANAGER') AND (LOWER(u.firstname) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :filter, '%'))) ORDER BY u.id DESC")
    List<User> findALlWorkersWithFilter(@Param("filter") String filter);
}
