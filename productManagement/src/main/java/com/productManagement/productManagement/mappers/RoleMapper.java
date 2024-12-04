package com.productManagement.productManagement.mappers;

import com.productManagement.productManagement.dto.requestDTO.RoleRequestDTO;
import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.RoleResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {

    RoleResponseDTO toDTO(Role role);

    Role toEntity(RoleRequestDTO roleRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(RoleRequestDTO roleRequestDTO, @MappingTarget Role role);

}
