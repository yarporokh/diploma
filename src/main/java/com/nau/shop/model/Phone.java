package com.nau.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
    @Id
    private Long id;
    @Column(unique = true)
    private String phone;
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
