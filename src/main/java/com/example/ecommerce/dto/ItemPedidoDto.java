package com.example.ecommerce.dto;

import com.example.ecommerce.model.Producto;

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
public class ItemPedidoDto {
    private int id;
    private Producto producto;
    private int cantidad;
    private double precio;
}
