package com.productManagement.productManagement.controller;

import com.productManagement.productManagement.dto.requestDTO.UserRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.UserResponseDTO;
import com.productManagement.productManagement.repository.RoleRepository;
import com.productManagement.productManagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {
    private final  UserService userService;
    private final RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {

        UserResponseDTO userResponseDTO = userService.save(userRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}
