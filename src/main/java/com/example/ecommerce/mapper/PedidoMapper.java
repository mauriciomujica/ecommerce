package com.example.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.model.Pedido;

@Mapper(componentModel = "spring", uses = {ItemPedidoMapper.class, ItemPedidoResponseMapper.class})
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    PedidoDto toDto(Pedido pedido);

    Pedido toEntity(PedidoDto pedidoDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "precioFinalPedido", ignore = true)
    Pedido toEntityFromCreate(PedidoCreateDto dto);
}
