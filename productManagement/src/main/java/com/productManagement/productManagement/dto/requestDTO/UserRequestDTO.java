package com.productManagement.productManagement.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
