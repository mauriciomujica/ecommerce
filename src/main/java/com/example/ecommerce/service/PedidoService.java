package com.example.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.exception.PedidoNoEncontradoException;
import com.example.ecommerce.mapper.PedidoMapper;
import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.repository.PedidoRepository;
import com.example.ecommerce.util.PedidoResponse;

@Service
public class PedidoService {
    private final PedidoRepository repo;
    private final PedidoMapper mapper;

    public PedidoService(PedidoRepository repo, PedidoMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public PedidoDto guardar(PedidoCreateDto p){
        Pedido pedidoAGuardar = repo.save(mapper.toEntityFromCreate(p));
        return mapper.toDto(pedidoAGuardar);
    }

    public PedidoResponse listarPedidos(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pedido> page = repo.findAll(pageable);
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
        Pedido pedido = repo.findById(id).orElseThrow(()-> new PedidoNoEncontradoException("No se encontró un pedido con la ID: " + id));
        return mapper.toDto(pedido);
    }
}
