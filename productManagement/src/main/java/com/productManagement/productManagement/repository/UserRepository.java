package com.productManagement.productManagement.repository;

import com.productManagement.productManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
}
