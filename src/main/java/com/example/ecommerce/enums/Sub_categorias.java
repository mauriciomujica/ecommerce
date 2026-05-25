package com.example.ecommerce.enums;

public enum Sub_categorias {
    // Almacen
    HARINAS(Categorias.ALMACEN),
    PASTAS(Categorias.ALMACEN),
    ACEITES(Categorias.ALMACEN),

    // Bebidas
    MINERALES(Categorias.BEBIDAS),
    SABORIZADAS(Categorias.BEBIDAS),
    GASEOSAS(Categorias.BEBIDAS),

    // Carnes
    CARNES(Categorias.CARNES),
    POLLOS(Categorias.CARNES),
    PESCADOS(Categorias.CARNES),

    // Congelados
    HAMBURGUESAS(Categorias.CONGELADOS),
    HELADOS(Categorias.CONGELADOS),
    NUGGETS(Categorias.CONGELADOS),

    // Lacteos
    LECHES(Categorias.LACTEOS),
    YOGURES(Categorias.LACTEOS),
    QUESOS(Categorias.LACTEOS);

    private final Categorias categoria;

    Sub_categorias(Categorias categoria) {
        this.categoria = categoria;
    }

    public Categorias getCategoria() {
        return categoria;
    }
}
