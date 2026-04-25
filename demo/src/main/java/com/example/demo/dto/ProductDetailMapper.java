package com.example.demo.dto;

import com.example.demo.Model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring", uses = {ImageMapper.class})
public interface ProductDetailMapper {

    ProductDetailDTO toDTO (Product product);

    List<ProductDetailDTO> toDTOs (Collection<Product> products);
    
    @Mapping(target = "image", ignore = true)
    Product toDomain(ProductDetailDTO productDetailDTO);


}
