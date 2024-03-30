package com.nau.shop.repository;

import com.nau.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findProductByCategory(Category category);
    Product findProductById(Long id);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :filter, '%')) OR lower(p.description) LIKE lower(concat('%', :filter, '%')) ORDER BY p.id DESC")
    List<Product> findByFilter(@Param("filter") String filter);
}
