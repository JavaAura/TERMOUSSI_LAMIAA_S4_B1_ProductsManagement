package com.productManagement.productManagement.services;

import com.productManagement.productManagement.dto.requestDTO.RoleRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.RoleResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.exceptions.RoleException;
import com.productManagement.productManagement.mappers.RoleMapper;
import com.productManagement.productManagement.repository.RoleRepository;
import com.productManagement.productManagement.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private RoleRequestDTO roleRequestDTO;
    private RoleResponseDTO roleResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setName("ROLE_USER");

        roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setId(1L);
        roleResponseDTO.setName("ROLE_USER");
    }

    @Test
    void createRole() {
        // Arrange
        when(roleMapper.toEntity(roleRequestDTO)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDTO(role)).thenReturn(roleResponseDTO);

        // Act
        RoleResponseDTO result = roleService.createRole(roleRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        verify(roleRepository).save(role);
        verify(roleMapper).toDTO(role);
    }

    @Test
    void getAllRoles() {
        // Arrange
        List<Role> roleList = List.of(role);
        List<RoleResponseDTO> roleResponseDTOList = roleList.stream()
                .map(r -> new RoleResponseDTO(r.getId(), r.getName()))
                .collect(Collectors.toList());
        when(roleRepository.findAll()).thenReturn(roleList);
        when(roleMapper.toDTO(role)).thenReturn(roleResponseDTO);

        // Act
        List<RoleResponseDTO> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(roleRepository).findAll();
        verify(roleMapper).toDTO(role);
    }

    @Test
    void getRoleById_ExistingRole() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(role)).thenReturn(roleResponseDTO);

        // Act
        RoleResponseDTO result = roleService.getRoleById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        verify(roleRepository).findById(1L);
    }

    @Test
    void getRoleById_RoleNotFound() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RoleException exception = assertThrows(RoleException.class, () -> roleService.getRoleById(1L));
        assertEquals("Role not found", exception.getMessage());
    }

    @Test
    void updateRole() {
        // Arrange
        RoleRequestDTO updatedRoleRequestDTO = new RoleRequestDTO();
        updatedRoleRequestDTO.setName("ROLE_ADMIN");
        Role updatedRole = new Role();
        updatedRole.setId(1L);
        updatedRole.setName("ROLE_ADMIN");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(updatedRole)).thenReturn(roleResponseDTO);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        when(roleRepository.save(roleArgumentCaptor.capture())).thenReturn(updatedRole);

        // Act
        RoleResponseDTO result = roleService.updateRole(1L, updatedRoleRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_ADMIN", result.getName());
        assertEquals("ROLE_ADMIN", roleArgumentCaptor.getValue().getName());
        verify(roleRepository).save(roleArgumentCaptor.getValue());
    }

    @Test
    void updateRole_RoleNotFound() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RoleException exception = assertThrows(RoleException.class, () -> roleService.updateRole(1L, roleRequestDTO));
        assertEquals("Role not found", exception.getMessage());
    }

    @Test
    void deleteRole() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(role);

        // Act
        roleService.deleteRole(1L);

        // Assert
        verify(roleRepository).delete(role);
    }

    @Test
    void deleteRole_RoleNotFound() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RoleException exception = assertThrows(RoleException.class, () -> roleService.deleteRole(1L));
        assertEquals("Role not found", exception.getMessage());
    }
}
