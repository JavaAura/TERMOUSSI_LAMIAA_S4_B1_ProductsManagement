package com.productManagement.productManagement.service;

import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.entities.Role;
import com.productManagement.productManagement.entities.User;
import com.productManagement.productManagement.exceptions.RoleException;
import com.productManagement.productManagement.exceptions.UserException;
import com.productManagement.productManagement.mappers.UserMapper;
import com.productManagement.productManagement.repository.RoleRepository;
import com.productManagement.productManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByLogin(userRequestDTO.getLogin())) {
            throw new RuntimeException("Login already exists");
        }
        User user = userMapper.toEntity(userRequestDTO);

        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (String roleName : userRequestDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            } else {
                throw new RoleException(roleName);
            }
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(id));

        userMapper.updateEntityFromDTO(userRequestDTO, existingUser);
        // Map roles from strings to Role entities
        if (userRequestDTO.getRoles() != null) {
            Set<Role> roles = userRequestDTO.getRoles().stream()
                    .map(roleName -> {
                        Role role = roleRepository.findByName(roleName);
                        if (role == null) {
                            throw new RoleException(roleName);
                        }
                        return role;
                    })
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(id));
        userRepository.delete(user);
    }
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
