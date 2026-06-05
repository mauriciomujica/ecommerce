package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.exception.PedidoNoEncontradoException;
import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository repo;

    public PedidoService(PedidoRepository repo){
        this.repo = repo;
    }

    public List<Pedido> listarPedidos(){
        return repo.findAll();
    }

    public Pedido obtenerPorID(int id){
        Pedido pedido = repo.findById(id).orElseThrow(()-> new PedidoNoEncontradoException("No se encontró un pedido con la ID: " + id));
        return pedido;
    }

//    public Pedido guardar(){

//    }
}
