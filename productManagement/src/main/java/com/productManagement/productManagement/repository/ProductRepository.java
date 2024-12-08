package com.productManagement.productManagement.repository;

import com.productManagement.productManagement.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByDesignationContainingIgnoreCase(String designation, Pageable pageable);
    Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
}
