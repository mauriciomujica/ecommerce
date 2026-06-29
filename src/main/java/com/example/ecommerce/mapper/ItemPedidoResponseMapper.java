package com.example.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.ecommerce.dto.ItemPedidoResponseDto;
import com.example.ecommerce.model.ItemPedido;

@Mapper(componentModel = "spring", uses = ProductoMapper.class)
public interface ItemPedidoResponseMapper {
    ItemPedidoResponseMapper INSTANCE = Mappers.getMapper(ItemPedidoResponseMapper.class);

    ItemPedidoResponseDto toResponseDto(ItemPedido itemPedido);
    
}

