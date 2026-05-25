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

@Entity
@Table
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

    public Producto() {}

    public Producto(String nombre, int stock, double precio, Categorias categoria, Sub_categorias subcategoria){
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categorias getCategoria(){
        return categoria;
    }

    public void setCategoria(Categorias categoria){
        this.categoria = categoria;
    }

    public Sub_categorias getSubcategoria(){
        return subcategoria;
    }

    public void setSubcategoria(Sub_categorias subcategoria){
        this.subcategoria = subcategoria;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | " + nombre +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoria: " + categoria +
                " | Subcategoria: " + subcategoria;

    }
}