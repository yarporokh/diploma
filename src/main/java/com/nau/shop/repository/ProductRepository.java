package com.nau.shop.repository;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByCategory(Category category);
    Product findProductById(Long id);
}
