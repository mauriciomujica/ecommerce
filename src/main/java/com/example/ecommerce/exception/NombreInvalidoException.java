package com.example.ecommerce.exception;

public class NombreInvalidoException extends RuntimeException{
    
    public NombreInvalidoException(String mensaje){
        super(mensaje);
    }
}
