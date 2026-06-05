package com.example.ecommerce.dto;

import com.example.ecommerce.enums.Categorias;
import com.example.ecommerce.enums.Sub_categorias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDto {
    private int id;
    private String nombre;
    private int stock;
    private double precio;
    private Categorias categoria;
    private Sub_categorias subcategoria;
}
