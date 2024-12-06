package com.productManagement.productManagement.exceptions;

public class ProductException extends RuntimeException{
    public ProductException(Long id){
        super("Product not found with the id :"+id);
    }
}
