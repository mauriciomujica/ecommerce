package com.example.ecommerce.model;
public class ItemPedido {
    private Producto item;
    private int cantidad;
    private double preciofinal;

    public ItemPedido(Producto item, int cantidad){
        this.item = item;
        this.cantidad = cantidad;
    }

    double getItemPrecioFinal(){
        preciofinal = item.getPrecio() * cantidad;
        return preciofinal;
    }
}
