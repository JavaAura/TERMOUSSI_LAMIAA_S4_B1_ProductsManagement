package com.productManagement.productManagement.mappers;

import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.entities.User;
import org.mapstruct.*;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    UserResponseDTO toDTO(User user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringsToRoles")
    User toEntity(UserRequestDTO userRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringsToRoles")
    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    // Custom mapping for roles (Entity to DTO)
    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    // Custom mapping for roles (DTO to Entity)
    @Named("stringsToRoles")
    default Set<Role> stringsToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream().map(name -> {
            Role role = new Role();
            role.setName(name);
            return role;
        }).collect(Collectors.toSet());
    }

}
