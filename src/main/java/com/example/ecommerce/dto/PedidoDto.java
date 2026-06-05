package com.example.ecommerce.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.ecommerce.model.ItemPedido;

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
public class PedidoDto {
    private int id;

    @Builder.Default
    private Set<ItemPedido> itemsPedido = new HashSet<>();
    
    private double precioFinalPedido;
}
