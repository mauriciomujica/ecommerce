package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.model.Producto;
import com.example.ecommerce.dao.ProductoRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo){
        this.repo = repo;
    }

    public Producto guardar(Producto p){
        return repo.save(p);
    }

    public List<Producto> listarTodos(){
        return repo.findAll();
    }
    
    public Producto obtenerPorID(int id) {
        return repo.findById(id).orElse(null);
    }

    public Producto actualizar(int id, Producto datos){
        Producto p = obtenerPorID(id);
        if (p != null){
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            p.setStock(datos.getStock());
            return repo.save(p);
        }
        return null;
    }

    public boolean eliminar(int id){
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}