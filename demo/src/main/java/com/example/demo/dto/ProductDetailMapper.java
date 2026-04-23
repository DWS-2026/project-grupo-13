package com.example.demo.dto;

import com.example.demo.Model.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring", uses = {ImageMapper.class})
public interface ProductDetailMapper {

    ProductDetailDTO toDTO (Product product);
    
}
