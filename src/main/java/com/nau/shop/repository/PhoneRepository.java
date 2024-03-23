package com.nau.shop.repository;

import com.nau.shop.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    Optional<Phone> findByPhone(String phone);
}
