package com.example.ecommerce.dto;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class ItemPedidoResponseDto {
    private int id;
    private int cantidad;
    private double precio;
    private ProductoDto producto;  // Full product details
}