package com.example.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.example.ecommerce.dto.ProductoCreateDto;
import com.example.ecommerce.dto.ProductoDto;
import com.example.ecommerce.dto.ProductoUpdateDto;
import com.example.ecommerce.model.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDto toDto(Producto producto);
    
    Producto toEntity(ProductoDto productoDto);

    Producto toEntityFromCreate(ProductoCreateDto dto);
    
    //@Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarProductoEntityDesdeDto(ProductoUpdateDto productoDto, @MappingTarget Producto producto);
}
