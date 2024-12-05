package com.productManagement.productManagement.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDTO {
    @NotNull(message = "login cannot be null")
    private String login;

    @NotNull(message = "password cannot be null")
    private String password;

    private boolean active = true;

    @NotNull(message = "role cannot be null")
    private Set<String> roles;
}
