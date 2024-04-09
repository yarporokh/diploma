package com.nau.shop.controller.rest;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.nau.shop.service.ProductService.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

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

    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable("category") Category category) {
        return productService.findByCategory(category);
    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProductsPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "12") int size) {
        return ResponseEntity.ok().body(productService.getAllProducts(page, size));
    }

    @PostMapping("/upload-photo")
    public void uploadPhoto(@RequestParam("photo") MultipartFile photo,@RequestParam("id") Long id) {
        productService.updatePhoto(id, photo);
    }

    @GetMapping(value = "/image/{filename}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }
}
