package com.example.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ProductoCreateDto;
import com.example.ecommerce.dto.ProductoDto;
import com.example.ecommerce.dto.ProductoUpdateDto;
import com.example.ecommerce.service.ProductoService;
import com.example.ecommerce.util.ProductoResponse;

import io.micrometer.common.util.StringUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<ProductoResponse> obtenerProductos(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
        @RequestParam(required=false) String nombre){
        if (nombre == null || StringUtils.isEmpty(nombre)){
            return new ResponseEntity<>(service.listarTodos(pageNumber, pageSize), HttpStatus.OK);
        }
        else return new ResponseEntity<>(service.obtenerPorNombre(pageNumber, pageSize, nombre), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerPorID(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorID(id));
    }

    @PostMapping()
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody ProductoCreateDto productoDto) {
        return new ResponseEntity<>(service.guardar(productoDto), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable int id, @RequestBody ProductoUpdateDto productoDto) {       
        ProductoDto productoActualizado = service.actualizar(id, productoDto); 
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable int id){
        service.eliminar(id);
        return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
    }
    
}
