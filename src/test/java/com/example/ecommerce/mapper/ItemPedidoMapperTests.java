package com.example.ecommerce.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.model.ItemPedido;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ItemPedidoMapperImpl.class})
public class ItemPedidoMapperTests {

    @Autowired
    ItemPedidoMapper mapper;

    @Test
    public void DtoEntityMismoIDyCantidad(){

        ItemPedidoDto dto = new ItemPedidoDto();
        dto.setId(1);
        dto.setCantidad(5);

        ItemPedido entity = mapper.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCantidad(), entity.getCantidad());
    }
}
