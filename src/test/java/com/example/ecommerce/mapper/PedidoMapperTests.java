package com.example.ecommerce.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.model.Pedido;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PedidoMapperImpl.class, ItemPedidoMapperImpl.class})
public class PedidoMapperTests {
    
    @Autowired
    PedidoMapper mapper;

    @Test
    public void CrearPedidoFromPedidoCreateDto_ReturnPedido(){
        
        List<ItemPedidoDto> items = new ArrayList<>();

        ItemPedidoDto item1 = ItemPedidoDto.builder()
        .id(1).cantidad(5).build();

        ItemPedidoDto item2 = ItemPedidoDto.builder()
        .id(3).cantidad(3).build();

        items.add(item1);
        items.add(item2);

        PedidoCreateDto dto = PedidoCreateDto.builder()
        .itemsPedido(items).build();

        Pedido pedido = mapper.toEntityFromCreate(dto);

        assertNotNull(pedido);
        assertNotNull(pedido.getItemsPedido());
        assertEquals(2, pedido.getItemsPedido().size());

        assertEquals(1, pedido.getItemsPedido().get(0).getId());
        assertEquals(5, pedido.getItemsPedido().get(0).getCantidad());
        assertEquals(3, pedido.getItemsPedido().get(1).getId());
        assertEquals(3, pedido.getItemsPedido().get(1).getCantidad());
        
    }
}
