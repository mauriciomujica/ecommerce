package com.example.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce.model.Producto;
import com.example.ecommerce.repository.ProductoRepository;
import com.example.ecommerce.util.ProductoResponse;
import com.example.ecommerce.dto.ProductoCreateDto;
import com.example.ecommerce.dto.ProductoDto;
import com.example.ecommerce.dto.ProductoUpdateDto;
import com.example.ecommerce.exception.CantidadInvalidaException;
import com.example.ecommerce.exception.NombreInvalidoException;
import com.example.ecommerce.exception.ProductoNoEncontradoException;
import com.example.ecommerce.mapper.ProductoMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoMapper mapper;
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo, ProductoMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public ProductoDto guardar(ProductoCreateDto p){
        Producto productoAGuardar = mapper.toEntityFromCreate(p);

        if (productoAGuardar.getNombre() == null || productoAGuardar.getNombre().isBlank()){
            throw new NombreInvalidoException("El nombre del producto no puede estar vacio");
        }

        if (productoAGuardar.getStock() <= 0){
            throw new CantidadInvalidaException("El stock del producto es inválido, tiene que ser mayor a 0");
        }

        if (productoAGuardar.getPrecio() <= 0){
            throw new CantidadInvalidaException("El precio del producto es inválido, tiene que ser mayor a 0");
        }

        repo.save(productoAGuardar);

        return mapper.toDto(productoAGuardar);
    }

    public ProductoResponse listarTodos(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Producto> page = repo.findAll(pageable);
        List<Producto> listaProductos = page.getContent();
        List<ProductoDto> content = listaProductos.stream().map(p -> mapper.toDto(p)).collect(Collectors.toList());

        ProductoResponse productoResponse = new ProductoResponse();
        productoResponse.setContent(content);
        productoResponse.setPageNumber(page.getNumber());
        productoResponse.setPageSize(page.getSize());
        productoResponse.setTotalElements(page.getTotalElements());
        productoResponse.setLastPage(page.isLast());

        return productoResponse;
    }
    
    public ProductoDto obtenerPorID(int id){
        Producto producto = repo.findById(id).orElseThrow(()-> new ProductoNoEncontradoException("No se encontró un producto con la ID: " + id));
        return mapper.toDto(producto);
    }

    public ProductoResponse obtenerPorNombre(int pageNumber, int pageSize, String nombre){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Producto> page = repo.findBynombreContaining(pageable, nombre);
        List<Producto> listaProductos = page.getContent();
        List<ProductoDto> content = listaProductos.stream().map(p -> mapper.toDto(p)).collect(Collectors.toList());

        ProductoResponse productoResponse = new ProductoResponse();
        productoResponse.setContent(content);
        productoResponse.setPageNumber(page.getNumber());
        productoResponse.setPageSize(page.getSize());
        productoResponse.setTotalElements(page.getTotalElements());
        productoResponse.setLastPage(page.isLast());

        return productoResponse;
    }

    public ProductoDto actualizar(int id, ProductoUpdateDto productoDto){
        Producto producto = repo.findById(id).orElseThrow(() -> new ProductoNoEncontradoException("No se encontró un producto con la ID: " + id));
        mapper.actualizarProductoEntityDesdeDto(productoDto, producto);
        producto = repo.save(producto);
        return mapper.toDto(producto);
    }

    public void eliminar(int id){
        Producto producto = repo.findById(id).orElseThrow(() -> new ProductoNoEncontradoException("El producto no pudo ser eliminado"));
        repo.delete(producto);
    }
}