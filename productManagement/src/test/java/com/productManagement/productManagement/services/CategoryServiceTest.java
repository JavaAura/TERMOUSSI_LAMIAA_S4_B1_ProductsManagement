//package com.productManagement.productManagement.services;
//
//import com.productManagement.productManagement.dto.requestDTO.CategoryRequestDTO;
//import com.productManagement.productManagement.dto.responseDTO.CategoryResponseDTO;
//import com.productManagement.productManagement.entities.Category;
//import com.productManagement.productManagement.exceptions.CategoryException;
//import com.productManagement.productManagement.mappers.CategoryMapper;
//import com.productManagement.productManagement.repository.CategoryRepository;
//import com.productManagement.productManagement.service.CategoryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class CategoryServiceTest {
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private CategoryMapper categoryMapper;
//
//    private CategoryService categoryService;
//
//    private CategoryRequestDTO categoryRequestDTO;
//    private CategoryResponseDTO categoryResponseDTO;
//    private Category category;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Initialize test data
//        categoryRequestDTO = new CategoryRequestDTO("Category Name", "Category Description");
//        categoryResponseDTO = new CategoryResponseDTO(1L, "Category Name", "Category Description");
//
//        category = new Category();
//        category.setId(1L);
//        category.setName("Category Name");
//        category.setDescription("Category Description");
//
//        // Initialize CategoryService with mocked dependencies
//        categoryService = new CategoryService(categoryRepository, categoryMapper);
//    }
//
//    @Test
//    void testCreateCategory() {
//        // Arrange: Set up categoryRepository to return the category when save() is called
//        when(categoryMapper.toEntity(categoryRequestDTO)).thenReturn(category);
//        when(categoryRepository.save(category)).thenReturn(category);
//        when(categoryMapper.toDTO(category)).thenReturn(categoryResponseDTO);
//
//        // Act: Call the createCategory method
//        CategoryResponseDTO result = categoryService.createCategory(categoryRequestDTO);
//
//        // Assert: Verify the result
//        assertNotNull(result);
//        assertEquals(categoryResponseDTO.getId(), result.getId());
//        assertEquals(categoryResponseDTO.getName(), result.getName());
//        assertEquals(categoryResponseDTO.getDescription(), result.getDescription());
//
//        // Verify that categoryRepository.save() was called once
//        verify(categoryRepository, times(1)).save(any(Category.class));
//
//        // Verify that categoryMapper.toEntity() and categoryMapper.toDTO() were called correctly
//        verify(categoryMapper, times(1)).toEntity(categoryRequestDTO);
//        verify(categoryMapper, times(1)).toDTO(category);
//    }
//
//    @Test
//    void testUpdateCategory() {
//        // Arrange: Set up mocks for updating category
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//        when(categoryMapper.updateEntityFromDTO(categoryRequestDTO, category)).thenReturn(category);
//        when(categoryRepository.save(category)).thenReturn(category);
//        when(categoryMapper.toDTO(category)).thenReturn(categoryResponseDTO);
//
//        // Act: Call the updateCategory method
//        CategoryResponseDTO result = categoryService.updateCategory(1L, categoryRequestDTO);
//
//        // Assert: Verify the result
//        assertNotNull(result);
//        assertEquals(categoryResponseDTO.getId(), result.getId());
//        assertEquals(categoryResponseDTO.getName(), result.getName());
//        assertEquals(categoryResponseDTO.getDescription(), result.getDescription());
//
//        // Verify that categoryRepository.findById() and categoryRepository.save() were called once
//        verify(categoryRepository, times(1)).findById(1L);
//        verify(categoryRepository, times(1)).save(any(Category.class));
//
//        // Verify that categoryMapper.updateEntityFromDTO() and categoryMapper.toDTO() were called correctly
//        verify(categoryMapper, times(1)).updateEntityFromDTO(categoryRequestDTO, category);
//        verify(categoryMapper, times(1)).toDTO(category);
//    }
//
//    @Test
//    void testGetCategoryById() {
//        // Arrange: Set up mocks for getting category by id
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//        when(categoryMapper.toDTO(category)).thenReturn(categoryResponseDTO);
//
//        // Act: Call the getCategoryById method
//        CategoryResponseDTO result = categoryService.getCategoryById(1L);
//
//        // Assert: Verify the result
//        assertNotNull(result);
//        assertEquals(categoryResponseDTO.getId(), result.getId());
//        assertEquals(categoryResponseDTO.getName(), result.getName());
//        assertEquals(categoryResponseDTO.getDescription(), result.getDescription());
//
//        // Verify that categoryRepository.findById() was called once
//        verify(categoryRepository, times(1)).findById(1L);
//        verify(categoryMapper, times(1)).toDTO(category);
//    }
//
//    @Test
//    void testGetAllCategories() {
//        // Arrange: Set up mocks for getting all categories
//        when(categoryRepository.findAll()).thenReturn(List.of(category));
//        when(categoryMapper.toDTO(category)).thenReturn(categoryResponseDTO);
//
//        // Act: Call the getAllCategories method
//        List<CategoryResponseDTO> result = categoryService.getAllCategories();
//
//        // Assert: Verify the result
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(categoryResponseDTO.getId(), result.get(0).getId());
//        assertEquals(categoryResponseDTO.getName(), result.get(0).getName());
//        assertEquals(categoryResponseDTO.getDescription(), result.get(0).getDescription());
//
//        // Verify that categoryRepository.findAll() was called once
//        verify(categoryRepository, times(1)).findAll();
//        verify(categoryMapper, times(1)).toDTO(category);
//    }
//
//    @Test
//    void testDeleteCategory() {
//        // Arrange: Set up mocks for category deletion
//        when(categoryRepository.existsById(1L)).thenReturn(true);
//
//        // Act: Call the deleteCategory method
//        categoryService.deleteCategory(1L);
//
//        // Assert: Verify that deleteById was called once
//        verify(categoryRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteCategoryNotFound() {
//        // Arrange: Set up mocks for category not found
//        when(categoryRepository.existsById(1L)).thenReturn(false);
//
//        // Act & Assert: Verify that CategoryException is thrown
//        assertThrows(CategoryException.class, () -> categoryService.deleteCategory(1L));
//
//        // Verify that deleteById was not called
//        verify(categoryRepository, times(0)).deleteById(1L);
//    }
//}
