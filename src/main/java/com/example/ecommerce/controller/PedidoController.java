package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.service.PedidoService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @GetMapping
    public List<Pedido> obtenerPedidos(){
        return service.listarPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPorID(@PathVariable int id){
        return service.obtenerPorID(id);
    }

//    @PostMapping(("/nuevo"))
//    public Pedido crearPedido(@RequestBody Pedido pedido){
        
//    }
}
