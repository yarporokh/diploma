package com.nau.shop;

import com.nau.shop.model.Category;
import com.nau.shop.model.Product;
import com.nau.shop.model.Role;
import com.nau.shop.model.User;
import com.nau.shop.service.ProductService;
import com.nau.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class ShopApplication implements CommandLineRunner {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        productService.save(Product.builder()
                .name("Протеїн")
                .description("Високобілковий")
                .category(Category.PROTEIN)
                .price(12.3)
                .quantity(31)
                .build());
        productService.save(Product.builder()
                .name("Креатин")
                .description("Моногідрат")
                .category(Category.CREATINE)
                .price(52.3)
                .quantity(22)
                .build());
        productService.save(Product.builder()
                .name("BCAA")
                .description("Б")
                .category(Category.AMINOCYCLOTES)
                .price(76.3)
                .quantity(2)
                .build());

        userService.save(User.builder()
                .firstname("Adam")
                .lastname("Walker")
                .email("adamwalker@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .role(Role.MANAGER)
                .isEnabled(true)
                .build());

        userService.save(User.builder()
                .firstname("Kim")
                .lastname("Jung")
                .email("kimjung@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .role(Role.ADMIN)
                .isEnabled(true)
                .build());

        userService.save(User.builder()
                .firstname("Joe")
                .lastname("Biden")
                .email("joebiden@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .role(Role.ADMIN)
                .isEnabled(false)
                .build());
    }
}
