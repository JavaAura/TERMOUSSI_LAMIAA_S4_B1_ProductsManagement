package com.productManagement.productManagement.mappers;

import com.productManagement.productManagement.dto.requestDTO.CategoryRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.CategoryResponseDTO;
import com.productManagement.productManagement.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CategoryRequestDTO categoryRequestDTO, @MappingTarget Category category);
}

