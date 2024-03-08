package com.nau.shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
/*@Table(name = "Product", indexes = {
        @Index(name = "idx_product_category", columnList = "category")
})*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @PositiveOrZero
    private Double price;
    @Enumerated(EnumType.STRING)
    private Category category;
    @PositiveOrZero
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
