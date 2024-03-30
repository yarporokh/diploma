package com.nau.shop.controller.rest;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/filter/{filter}")
    public List<Product> getByFilter(@PathVariable("filter") String filter) {
        return productService.findByFilter(filter);
    }

/*    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable("category") Category category) {
        return productService.findByCategory(category);
    }*/

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        productService.save(product);
    }

}
