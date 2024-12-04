package com.productManagement.productManagement.service;

import com.productManagement.productManagement.dto.requestDTO.RoleRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.RoleResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.exceptions.RoleException;
import com.productManagement.productManagement.mappers.RoleMapper;
import com.productManagement.productManagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        Role role = roleMapper.toEntity(roleRequestDTO);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toDTO(savedRole);
    }

    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()->new RoleException(id));
        return roleMapper.toDTO(role);
    }

    public RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(id).orElseThrow(()->new RoleException(id));
        roleMapper.updateEntityFromDTO(roleRequestDTO,role);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    public void deleteRole(Long id){
        Role role = roleRepository.findById(id).orElseThrow(()->new RoleException(id));
        roleRepository.delete(role);
    }
}
