package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Producto;
import com.example.ecommerce.service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Producto> listarTodos() {
        return service.listarTodos();
    }
    
    @GetMapping("/{id}")
    public Producto obtenerPorID(@PathVariable int id) {
        return service.obtenerPorID(id);
    }

    @PostMapping()
    public Producto crearProducto(@RequestBody Producto nuevo) {
        return service.guardar(nuevo);
    }
    
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto datos) {        
        return service.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable int id){
        service.eliminar(id);
    }
    
}
