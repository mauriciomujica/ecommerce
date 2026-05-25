package com.example.ecommerce.model;
import java.util.ArrayList;

public class Pedido {
    private ArrayList<ItemPedido> pedido;
    private double precioFinalPedido;

    Pedido(ArrayList<ItemPedido> pedido){
        this.pedido = pedido;
    }

    public void agregar_item(ItemPedido item){
        pedido.add(item);
    }

    public double calcular_precio(){
        precioFinalPedido = 0;
        for (ItemPedido item : pedido){
             precioFinalPedido +=item.getItemPrecioFinal();
        }
        return precioFinalPedido;
    }
}
