package com.example.demo.dto.mapper;
import com.example.demo.Model.Product;
import com.example.demo.dto.ProductBasicDTO;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring")
public interface ProductBasicMapper {

    List<ProductBasicDTO> toDTOs(Collection<Product> products);

    ProductBasicDTO toDTO(Product product);

}
