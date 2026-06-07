package com.example.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.model.ItemPedido;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedidoDto toDto(ItemPedido itemPedido);

    ItemPedido toEntity(ItemPedidoDto itemPedidoDto);
}
