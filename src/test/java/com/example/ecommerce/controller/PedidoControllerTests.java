package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.service.PedidoService;
import com.example.ecommerce.util.PedidoResponse;

import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTests {

    @Mock
    private PedidoService service;

    @InjectMocks
    private PedidoController controller;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private PedidoCreateDto create_dto;
    private PedidoDto pedido_dto;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();

        List<ItemPedidoDto> items = new ArrayList<>();

        ItemPedidoDto item1 = ItemPedidoDto.builder()
        .id(1).cantidad(5).build();

        ItemPedidoDto item2 = ItemPedidoDto.builder()
        .id(3).cantidad(3).build();

        items.add(item1);
        items.add(item2);

        create_dto = PedidoCreateDto.builder().itemsPedido(items).build();

        pedido_dto = PedidoDto.builder().id(1).itemsPedido(items).build();
    }

    @Test
    public void PedidoController_CrearPedido_ReturnsPedidoDto() throws Exception{
        when(service.guardar(ArgumentMatchers.any(PedidoCreateDto.class))).thenReturn(pedido_dto);

        mockMvc.perform(post("/pedidos/nuevo").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(create_dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("itemsPedido").isNotEmpty())
                .andExpect(jsonPath("itemsPedido", hasSize(2)))
                .andExpect(jsonPath("itemsPedido[0].id").value(1))
                .andExpect(jsonPath("itemsPedido[0].cantidad").value(5));
    }

    @Test
    public void PedidoController_ObtenerPorId_ReturnsPedidoDto() throws Exception{
        int id = 1;

        when(service.obtenerPorID(id)).thenReturn(pedido_dto);
        
        mockMvc.perform(get("/pedidos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("itemsPedido[0].id").value(1))
                .andExpect(jsonPath("itemsPedido[0].cantidad").value(5));
    }

    @Test
    public void PedidoController_ObtenerTodos_ReturnsPedidoResponse() throws Exception{
        List<PedidoDto> pedidos = new ArrayList<>();
        pedidos.add(pedido_dto);

        PedidoResponse response = PedidoResponse.builder().pageNumber(0).pageSize(10).content(pedidos).build();
        when(service.listarPedidos(1, 10)).thenReturn(response);

        mockMvc.perform(get("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content.size()").value(response.getContent().size()))
                .andExpect(jsonPath("content.[0].itemsPedido", hasSize(2)))
                .andExpect(jsonPath("content.[0].itemsPedido[0].cantidad").value(5));
    }
}
