package com.example.ecommerce.dto;

import com.example.ecommerce.enums.Categorias;
import com.example.ecommerce.enums.Sub_categorias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoUpdateDto {
    private Integer id;
    private String nombre;
    private Integer stock;
    private Double precio;
    private Categorias categoria;
    private Sub_categorias subcategoria;
}
