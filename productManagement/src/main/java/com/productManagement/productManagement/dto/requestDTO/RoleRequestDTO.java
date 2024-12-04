package com.productManagement.productManagement.dto.requestDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleRequestDTO {
    @NotBlank(message = "Role name is mandatory")
    private String name;
}
