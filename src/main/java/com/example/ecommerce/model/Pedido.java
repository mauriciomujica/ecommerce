package com.example.ecommerce.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itemsPedido;
    
    @Column(name="precio_final")
    private double precioFinalPedido;


    public void agregar_item(ItemPedido item){
        if (item != null){
            itemsPedido.add(item);
            item.setPedido(this);
        }
    }

    public void calc_precio_final(ItemPedido item){
        double precio = item.getPrecio();
        int cantidad = item.getCantidad();
        precioFinalPedido += precio*cantidad;
    }
}
