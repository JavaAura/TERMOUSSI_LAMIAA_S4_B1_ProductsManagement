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
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToRoleNames")
    UserResponseDTO toDTO(User user);

    // Map UserRequestDTO to User entity
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNamesToRoles")
    User toEntity(UserRequestDTO userRequestDTO);

    // Update User entity using UserRequestDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNamesToRoles")
    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    // Custom mapping: Convert Role entities to role names (String)
    @Named("rolesToRoleNames")
    default Set<String> rolesToRoleNames(Collection<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    // Custom mapping: Convert role names (Strings) to Role entities
    @Named("roleNamesToRoles")
    default Set<Role> roleNamesToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
