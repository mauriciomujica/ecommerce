package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.PedidoCreateDto;
import com.example.ecommerce.dto.PedidoDto;
import com.example.ecommerce.service.PedidoService;
import com.example.ecommerce.util.PedidoResponse;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PedidoResponse> obtenerPedidos(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
        return new ResponseEntity<>(service.listarPedidos(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> obtenerPorID(@PathVariable int id){
        return ResponseEntity.ok(service.obtenerPorID(id));
    }

    @PostMapping("/nuevo")
    public ResponseEntity<PedidoDto> crearPedido(@RequestBody PedidoCreateDto pedido){
        return new ResponseEntity<>(service.guardar(pedido), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(@PathVariable int id){
        service.eliminarPedido(id);
        return new ResponseEntity<>("Pedido eliminado", HttpStatus.OK);
    }
}
