package com.productManagement.productManagement.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestDTO {

    @NotBlank(message = "Designation is mandatory")
    @Size(max = 100, message = "Designation cannot exceed 100 characters")
    private String designation;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

}
