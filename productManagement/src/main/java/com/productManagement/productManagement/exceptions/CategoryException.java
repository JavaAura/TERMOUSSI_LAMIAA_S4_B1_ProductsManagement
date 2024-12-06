package com.productManagement.productManagement.exceptions;

public class CategoryException extends RoleException{
    public CategoryException(Long id){
        super("Category not found with the id :"+id);
    }

}
