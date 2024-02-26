package com.nau.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterBody {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
}
