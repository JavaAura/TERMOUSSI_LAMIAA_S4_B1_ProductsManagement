package com.productManagement.productManagement.repository;

import com.productManagement.productManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.jws.soap.SOAPBinding;

public interface UserRepository extends JpaRepository<User,Long> {
}
