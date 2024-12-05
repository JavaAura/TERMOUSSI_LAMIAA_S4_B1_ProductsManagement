package com.productManagement.productManagement.exceptions;

public class RoleException extends RuntimeException{
    public RoleException(String name){
        super("Role not found with the name: "+ name);
    }
}
