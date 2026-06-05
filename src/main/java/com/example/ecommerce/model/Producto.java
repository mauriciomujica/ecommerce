package com.example.ecommerce.model;

import com.example.ecommerce.enums.Categorias;
import com.example.ecommerce.enums.Sub_categorias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "stock")
    private int stock;

    @Column(name = "precio")
    private double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categorias categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "subcategoria")
    private Sub_categorias subcategoria;
}