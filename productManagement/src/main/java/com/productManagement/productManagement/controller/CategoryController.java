package com.productManagement.productManagement.controller;

import com.productManagement.productManagement.dto.requestDTO.CategoryRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.CategoryResponseDTO;
import com.productManagement.productManagement.service.CategoryService;
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
@RequestMapping("/user/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO responseDTO = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO responseDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getCategories(Pageable pageable) {
        Page<CategoryResponseDTO> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/categories/searchByName")
    public ResponseEntity<Page<CategoryResponseDTO>> searchCategoriesByName(
            @RequestParam String name, Pageable pageable) {

        Page<CategoryResponseDTO> categories = categoryService.searchCategoriesByName(name, pageable);

        return ResponseEntity.ok(categories);
    }
}
