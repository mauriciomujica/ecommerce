package com.example.ecommerce.dto;

import java.util.HashSet;
import java.util.Set;

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
public class PedidoCreateDto {

    @Builder.Default
    private Set<ItemPedidoDto> itemsPedido = new HashSet<>();
}
