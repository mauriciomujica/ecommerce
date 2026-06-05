package com.example.ecommerce.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import com.example.ecommerce.model.Producto;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductoRepositoryTests {
    
    @Autowired
    private ProductoRepository repo;

    @Test
    public void ProductoRepository_FindByNombre_ReturnProductoNotNull(){

        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Producto producto = Producto.builder()
        .nombre("test")
        .precio(2.200)
        .stock(10)
        .build();

        repo.save(producto);

        Page<Producto> page = repo.findBynombreContaining(pageable, producto.getNombre());
        List<Producto> result = page.getContent();

        Assertions.assertThat(result).isNotNull();
    }
}
