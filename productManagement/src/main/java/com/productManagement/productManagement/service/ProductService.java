package com.productManagement.productManagement.service;

import com.productManagement.productManagement.dto.requestDTO.ProductRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.ProductResponseDTO;
import com.productManagement.productManagement.entities.Product;
import com.productManagement.productManagement.exceptions.ProductException;
import com.productManagement.productManagement.mappers.ProductMapper;
import com.productManagement.productManagement.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(productId));

        productMapper.updateEntityFromDTO(productRequestDTO, existingProduct);
        existingProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(existingProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDTOList(products);
    }

    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(productId));
        return productMapper.toDTO(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(productId));
        productRepository.delete(product);
    }
}
