package com.nau.shop.service;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findProductByCategory(category);
    }

    public Product findById(Long id) {
        return productRepository.findProductById(id);
    }
}
