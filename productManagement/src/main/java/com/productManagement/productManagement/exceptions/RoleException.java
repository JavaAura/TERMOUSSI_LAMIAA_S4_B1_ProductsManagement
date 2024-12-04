package com.productManagement.productManagement.exceptions;

public class RoleException extends RuntimeException{
    public RoleException(Long id ){
        super("Role not found with the id"+ id);
    }
}
