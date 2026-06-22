package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ItemPedidoDto;
import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.exception.PedidoNoEncontradoException;
import com.example.ecommerce.exception.ProductoNoEncontradoException;
import com.example.ecommerce.mapper.PedidoMapper;
import com.example.ecommerce.model.ItemPedido;
import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.model.Producto;
import com.example.ecommerce.repository.PedidoRepository;
import com.example.ecommerce.repository.ProductoRepository;
import com.example.ecommerce.util.PedidoResponse;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;
    private final PedidoMapper mapper;

    public PedidoService(PedidoRepository pedidoRepo, ProductoRepository productoRepo, PedidoMapper mapper){
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
        this.mapper = mapper;
    }

    public PedidoDto guardar(PedidoCreateDto p){
        Pedido pedidoAGuardar = mapper.toEntityFromCreate(p);
        pedidoAGuardar.setItemsPedido(new ArrayList<>());

        for (ItemPedidoDto itemDto : p.getItemsPedido()) {
            Producto producto = productoRepo.findById(itemDto.getId())
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));
            
            ItemPedido item = ItemPedido.builder()
                .producto(producto)
                .cantidad(itemDto.getCantidad())
                .precio(producto.getPrecio())
                .pedido(pedidoAGuardar)
                .build();
            pedidoAGuardar.agregar_item(item);
            pedidoAGuardar.calc_precio_final(item);
        }
        pedidoRepo.save(pedidoAGuardar);
        return mapper.toDto(pedidoAGuardar);
    }

    public PedidoResponse listarPedidos(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pedido> page = pedidoRepo.findAll(pageable);
        List<Pedido> listaPedidos = page.getContent();
        List<PedidoDto> content = listaPedidos.stream().map(p -> mapper.toDto(p)).collect(Collectors.toList());

        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setContent(content);
        pedidoResponse.setPageNumber(page.getNumber());
        pedidoResponse.setPageSize(page.getSize());
        pedidoResponse.setTotalElements(page.getTotalElements());
        pedidoResponse.setLastPage(page.isLast());

        return pedidoResponse;
    }

    public PedidoDto obtenerPorID(int id){
        Pedido pedido = pedidoRepo.findById(id).orElseThrow(()-> new PedidoNoEncontradoException("No se encontró un pedido con la ID: " + id));
        return mapper.toDto(pedido);
    }
}
