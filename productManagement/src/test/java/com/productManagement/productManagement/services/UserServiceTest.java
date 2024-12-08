package com.productManagement.productManagement.services;

import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.entities.User;
import com.productManagement.productManagement.exceptions.UserException;
import com.productManagement.productManagement.mappers.UserMapper;
import com.productManagement.productManagement.repository.RoleRepository;
import com.productManagement.productManagement.repository.UserRepository;
import com.productManagement.productManagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        userRequestDTO = new UserRequestDTO("user1", "password", Set.of("ROLE_USER"));
        userResponseDTO = new UserResponseDTO(1L, "user1", Set.of("ROLE_USER"));
        user = new User();
        user.setId(1L);
        user.setLogin("user1");
        user.setPassword("password");
        user.setRoles(Set.of(new Role("ROLE_USER")));

        // Initialize UserService with mocked dependencies
        userService = new UserService(userRepository, passwordEncoder, userMapper, roleRepository);
    }

    @Test
    void testSave() {
        // Arrange: Set up mocks
        when(userRepository.existsByLogin(userRequestDTO.getLogin())).thenReturn(false);
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

        // Act: Call the save method
        UserResponseDTO result = userService.save(userRequestDTO);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(userResponseDTO.getLogin(), result.getLogin());
        assertEquals(userResponseDTO.getRoles(), result.getRoles());

        // Verify interactions
        verify(userRepository, times(1)).existsByLogin(userRequestDTO.getLogin());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testSave_LoginAlreadyExists() {
        // Arrange: Set up mock to simulate login already exists
        when(userRepository.existsByLogin(userRequestDTO.getLogin())).thenReturn(true);

        // Act & Assert: Verify exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.save(userRequestDTO));
        assertEquals("Login already exists", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).existsByLogin(userRequestDTO.getLogin());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        // Arrange: Set up mocks for updating user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.updateEntityFromDTO(userRequestDTO, user)).thenReturn(user);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

        // Act: Call the updateUser method
        UserResponseDTO result = userService.updateUser(1L, userRequestDTO);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(userResponseDTO.getLogin(), result.getLogin());
        assertEquals(userResponseDTO.getRoles(), result.getRoles());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange: Set up mock for user not found
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify exception is thrown
        UserException exception = assertThrows(UserException.class, () -> userService.updateUser(1L, userRequestDTO));
        assertEquals("User not found with ID: 1", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange: Set up mocks for deleting user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act: Call the deleteUser method
        userService.deleteUser(1L);

        // Assert: Verify that the delete method was called
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Arrange: Set up mock for user not found
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify exception is thrown
        UserException exception = assertThrows(UserException.class, () -> userService.deleteUser(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).delete(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        // Arrange: Set up mocks for getting all users
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

        // Act: Call the getAllUsers method
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert: Verify the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userResponseDTO.getLogin(), result.get(0).getLogin());
        assertEquals(userResponseDTO.getRoles(), result.get(0).getRoles());

        // Verify interactions
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testGetUserById() {
        // Arrange: Set up mocks for getting user by id
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

        // Act: Call the getUserById method
        UserResponseDTO result = userService.getUserById(1L);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(userResponseDTO.getLogin(), result.getLogin());
        assertEquals(userResponseDTO.getRoles(), result.getRoles());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testGetUserById_UserNotFound() {
        // Arrange: Set up mock for user not found
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify exception is thrown
        UserException exception = assertThrows(UserException.class, () -> userService.getUserById(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(0)).toDTO(any(User.class));
    }
}
