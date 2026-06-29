package com.example.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.dto.ItemPedidoResponseDto;
import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.mapper.PedidoMapper;
import com.example.ecommerce.model.ItemPedido;
import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.repository.PedidoRepository;
import com.example.ecommerce.service.PedidoService;
import com.example.ecommerce.util.PedidoResponse;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTests {
    
    @Mock
    private PedidoRepository repo;

    @Mock
    private PedidoMapper mapper;

    @InjectMocks
    private PedidoService service;

    private PedidoCreateDto create_dto;
    private PedidoDto pedido_dto;
    private Pedido pedido;

    @BeforeEach
    public void init(){
        List<ItemPedidoDto> items = new ArrayList<>();

        ItemPedidoDto item1 = ItemPedidoDto.builder()
        .id(1).cantidad(5).build();

        ItemPedidoDto item2 = ItemPedidoDto.builder()
        .id(3).cantidad(3).build();

        items.add(item1);
        items.add(item2);

        List<ItemPedidoResponseDto> items2 = new ArrayList<>();

        ItemPedidoResponseDto item3 = ItemPedidoResponseDto.builder()
        .id(1).cantidad(5).build();

        ItemPedidoResponseDto item4 = ItemPedidoResponseDto.builder()
        .id(3).cantidad(3).build();

        items2.add(item3);
        items2.add(item4);

        create_dto = PedidoCreateDto.builder().itemsPedido(items).build();

        pedido_dto = PedidoDto.builder().id(1).itemsPedido(items2).build();

        List<ItemPedido> itemsEnt = new ArrayList<>();

        ItemPedido item5 = ItemPedido.builder().id(1).cantidad(5).build();
        ItemPedido item6 = ItemPedido.builder().id(3).cantidad(3).build();

        itemsEnt.add(item5);
        itemsEnt.add(item6);

        pedido = Pedido.builder().id(1).itemsPedido(itemsEnt).build();
    }
    
    @Test
    public void PedidoService_CrearPedidoFromPedidoCreateDto_ReturnsPedidoDto(){
        when(mapper.toEntityFromCreate(Mockito.any(PedidoCreateDto.class))).thenReturn(pedido);
        when(mapper.toDto(Mockito.any(Pedido.class))).thenReturn(pedido_dto);
        when(repo.save(Mockito.any(Pedido.class))).thenReturn(pedido);

        PedidoDto result = service.guardar(create_dto);

        assertNotNull(result);
        assertEquals(2, result.getItemsPedido().size());

        assertEquals(create_dto.getItemsPedido().get(0).getId(), result.getItemsPedido().get(0).getId());
        assertEquals(create_dto.getItemsPedido().get(0).getCantidad(), result.getItemsPedido().get(0).getCantidad());

        verify(repo).save(Mockito.any(Pedido.class));
    }

    @Test
    public void PedidoService_listarPedidos_ReturnsPedidoResponse(){
        Page<Pedido> pedidos = Mockito.mock(Page.class);

        when(repo.findAll(Mockito.any(Pageable.class))).thenReturn(pedidos);

        PedidoResponse response = service.listarPedidos(0, 10);

        assertNotNull(response);
    }

    @Test
    public void PedidoService_obtenerPorId_ReturnsPedidoDto(){
        when(repo.findById(1)).thenReturn(Optional.of(pedido));
        when(mapper.toDto(Mockito.any(Pedido.class))).thenReturn(pedido_dto);

        PedidoDto result = service.obtenerPorID(1);

        assertNotNull(result);
        assertEquals(pedido_dto.getId(), result.getId());
        verify(repo).findById(1);
    }
}
