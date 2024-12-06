package com.productManagement.productManagement.mappers;

import com.productManagement.productManagement.dto.requestDTO.ProductRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.ProductResponseDTO;
import com.productManagement.productManagement.entities.Product;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toDTO(Product product);

    List<ProductResponseDTO> toDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    void updateEntityFromDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
