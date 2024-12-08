//package com.productManagement.productManagement.config;
//
//import com.productManagement.productManagement.entities.Role;
//import com.productManagement.productManagement.entities.User;
//import com.productManagement.productManagement.repository.RoleRepository;
//import com.productManagement.productManagement.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//@Component
//public class DataSeeder implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        seedUsers();
//    }
//
//    private void seedUsers() {
//        createUserIfNotExists("admin", "adminpswd", "ROLE_ADMIN");
//        createUserIfNotExists("user", "userpswd", "ROLE_USER");
//    }
//
//    private void createUserIfNotExists(String login, String password, String roleName) {
//        if (userRepository.findByLogin(login) == null) {
//            Role role = roleRepository.findByName(roleName);
//
//            if (role == null) {
//                role = new Role(roleName);
//                roleRepository.save(role);
//            }
//
//            // Create the new user
//            User newUser = new User();
//            newUser.setLogin(login);
//            newUser.setPassword(passwordEncoder.encode(password));
//            Set<Role> roles = new HashSet<>();
//            roles.add(role);
//            newUser.setRoles(roles);
//            userRepository.save(newUser);
//
//            System.out.println(login + " user created successfully with role " + role + "!");
//        }
//    }
//}
