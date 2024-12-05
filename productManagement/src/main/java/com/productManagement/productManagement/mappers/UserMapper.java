package com.productManagement.productManagement.mappers;

import com.productManagement.productManagement.dto.requestDTO.RoleRequestDTO;
import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.RoleResponseDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.entities.User;
import org.mapstruct.*;
import org.mapstruct.Named;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDTO userRequestDTO);

    @Mapping(source = "roles", target = "roles")
    UserResponseDTO toDTO(User user);
//    @Mapping(target = "id", ignore = true)
//    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);
default Set<String> map(Set<Role> roles) {
    return roles.stream()
            .map(Role::getName)
            .collect(Collectors.toSet());
}

}
