package com.example.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<String> handlePedidoNoEncontrado(PedidoNoEncontradoException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> handleProductoNoEncontrado(ProductoNoEncontradoException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> handleStockInsuficiente(StockInsuficienteException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CantidadInvalidaException.class)
    public ResponseEntity<String> handleCantidadInvalida(CantidadInvalidaException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
