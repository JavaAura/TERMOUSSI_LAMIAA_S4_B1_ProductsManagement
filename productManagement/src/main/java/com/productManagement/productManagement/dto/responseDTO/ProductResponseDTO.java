package com.productManagement.productManagement.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDTO {

    private Long id;
    private String designation;
    private Double price;
    private Integer quantity;
    private String categoryName;
}
