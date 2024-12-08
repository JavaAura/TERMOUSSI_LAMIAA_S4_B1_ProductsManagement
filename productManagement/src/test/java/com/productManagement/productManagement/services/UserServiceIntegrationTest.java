package com.productManagement.productManagement.services;

import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.entities.User;
import com.productManagement.productManagement.exceptions.UserException;
import com.productManagement.productManagement.repository.UserRepository;
import com.productManagement.productManagement.repository.RoleRepository;
import com.productManagement.productManagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserRequestDTO userRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        // Setup test data for UserRequestDTO
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setLogin("testuser");
        userRequestDTO.setPassword("testpassword");
        userRequestDTO.setRoles(List.of("ROLE_USER"));

        // Saving a user in the repository for testing purposes
        user = new User();
        user.setLogin("admin");
        user.setPassword(passwordEncoder.encode("adminpassword"));
        user.setRoles(roleRepository.findAll());
        userRepository.save(user);
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() {
        // Act
        UserResponseDTO result = userService.save(userRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getLogin());
        assertNotNull(result.getId());

        // Verify user is saved in the repository
        User savedUser = userRepository.findById(result.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getLogin());
        assertTrue(passwordEncoder.matches("testpassword", savedUser.getPassword()));
    }

    @Test
    void createUser_ShouldThrowExceptionWhenLoginExists() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.save(userRequestDTO));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Act
        List<UserResponseDTO> users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertTrue(users.size() > 0);
        assertTrue(users.stream().anyMatch(userDTO -> userDTO.getLogin().equals("admin")));
    }

    @Test
    void getUserById_ShouldReturnUserWhenFound() {
        // Act
        UserResponseDTO result = userService.getUserById(user.getId());

        // Assert
        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
    }

    @Test
    void getUserById_ShouldThrowExceptionWhenUserNotFound() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.getUserById(999L));
    }

    @Test
    void updateUser_ShouldUpdateUserSuccessfully() {
        // Arrange
        userRequestDTO.setPassword("newpassword");

        // Act
        UserResponseDTO result = userService.updateUser(user.getId(), userRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("newpassword", result.getPassword());

        // Verify user is updated in the repository
        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertTrue(passwordEncoder.matches("newpassword", updatedUser.getPassword()));
    }

    @Test
    void deleteUser_ShouldDeleteUserSuccessfully() {
        // Act
        userService.deleteUser(user.getId());

        // Assert
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    void deleteUser_ShouldThrowExceptionWhenUserNotFound() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.deleteUser(999L));
    }
}
