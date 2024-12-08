package com.productManagement.productManagement.services;

import com.productManagement.productManagement.dto.requestDTO.ProductRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.ProductResponseDTO;
import com.productManagement.productManagement.entities.Category;
import com.productManagement.productManagement.entities.Product;
import com.productManagement.productManagement.exceptions.ProductException;
import com.productManagement.productManagement.mappers.ProductMapper;
import com.productManagement.productManagement.repository.ProductRepository;
import com.productManagement.productManagement.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductRequestDTO productRequestDTO;
    private ProductResponseDTO productResponseDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Category category = new Category();
        category.setId(1L);
        category.setName("Category");

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setDesignation("Product Name");
        productRequestDTO.setPrice(100.0);

        productResponseDTO = new ProductResponseDTO(1L, "Product Name",
                100.0, 10, "Category");

        product = new Product();
        product.setId(1L);
        product.setDesignation("Product Name");
        product.setPrice(100.0);
        product.setCategory(category);
    }


    @Test
    void testCreateProduct() {
        when(productMapper.toEntity(productRequestDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.createProduct(productRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product Name", result.getDesignation());
        assertEquals(100.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals("Category", result.getCategoryName());

        verify(productMapper).toEntity(productRequestDTO);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productMapper).updateEntityFromDTO(productRequestDTO, product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.updateProduct(1L, productRequestDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product Name", result.getDesignation());
        assertEquals(100.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals("Category", result.getCategoryName());

        verify(productRepository).findById(1L);
        verify(productMapper).updateEntityFromDTO(productRequestDTO, product);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product);
        List<ProductResponseDTO> productResponseDTOs = Arrays.asList(productResponseDTO);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDTOList(products)).thenReturn(productResponseDTOs);

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product Name", result.get(0).getDesignation());
        assertEquals(100.0, result.get(0).getPrice());
        assertEquals("Category", result.get(0).getCategoryName());

        verify(productRepository).findAll();
        verify(productMapper).toDTOList(products);
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product Name", result.getDesignation());
        assertEquals(100.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals("Category", result.getCategoryName());

        verify(productRepository).findById(1L);
        verify(productMapper).toDTO(product);
    }


    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }



}
