package com.nau.shop.dto;

import com.nau.shop.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkerRegisterBody {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Role role;
}