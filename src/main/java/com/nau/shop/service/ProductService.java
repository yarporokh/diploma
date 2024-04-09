package com.nau.shop.service;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productRepository.findAll(sort);
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }

    public Product findById(Long id) {
        return productRepository.findProductById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findByFilter(String filter) {
        return productRepository.findByFilter(filter);
    }

    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }
}
