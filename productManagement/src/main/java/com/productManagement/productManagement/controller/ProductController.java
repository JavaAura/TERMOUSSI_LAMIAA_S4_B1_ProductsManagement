package com.productManagement.productManagement.controller;

import com.productManagement.productManagement.dto.requestDTO.ProductRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.ProductResponseDTO;
import com.productManagement.productManagement.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/products")
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(Pageable pageable) {
        Page<ProductResponseDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @RequestParam String designation, Pageable pageable) {
        Page<ProductResponseDTO> products = productService.searchProductsByDesignation(designation, pageable);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/products/searchByCategory")
    public ResponseEntity<Page<ProductResponseDTO>> searchProductsByCategory(
            @RequestParam String category, Pageable pageable) {

        Page<ProductResponseDTO> products = productService.searchProductsByCategory(category, pageable);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/products/filterByCategory")
    public ResponseEntity<Page<ProductResponseDTO>> filterProductsByCategory(
            @RequestParam String category, Pageable pageable) {

        Page<ProductResponseDTO> products = productService.filterProductsByCategory(category, pageable);
        return ResponseEntity.ok(products);
    }
}
