package com.example.demo.dto;
import com.example.demo.Model.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring")
public interface ProductBasicMapper {

    List<ProductBasicDTO> toDTOs(Collection<Product> products);

}
