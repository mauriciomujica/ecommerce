package com.example.ecommerce.model;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToMany(mappedBy = "pedido")
    private Set<ItemPedido> itemsPedido = new HashSet<>();
    
    @Column(name="precio_final")
    private double precioFinalPedido;


    public void agregar_item(ItemPedido item){
        if (item != null){
            if (itemsPedido == null){
                itemsPedido = new HashSet<>();
            }

            itemsPedido.add(item);
            item.setPedido(this);
        }
    }
}
