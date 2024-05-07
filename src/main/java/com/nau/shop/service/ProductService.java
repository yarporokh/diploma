package com.nau.shop.service;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public static final String PHOTO_DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

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
        if (product.getId() != null) {
            Product p = productRepository.findProductById(product.getId());
            product.setPhotoUrl(p.getPhotoUrl());
        }
        productRepository.save(product);
    }

    public List<Product> findByFilter(String filter) {
        return productRepository.findByFilter(filter);
    }

    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }

    public void updatePhoto(Long id, MultipartFile file) {
        Product product = productRepository.findProductById(id);
        String photoUrl = photoFunction.apply(id.toString(), file);
        product.setPhotoUrl(photoUrl);
        productRepository.save(product);
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (Files.notExists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/v1/product/image/" + filename).toUriString();
        } catch (Exception exception) {
            throw new RuntimeException("Unable to save image", exception);
        }
    };
}
