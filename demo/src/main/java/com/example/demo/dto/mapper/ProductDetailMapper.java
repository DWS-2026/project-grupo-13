package com.example.demo.dto.mapper;

import com.example.demo.Model.Product;
import com.example.demo.dto.ProductDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Collection;


@Mapper(
    componentModel = "spring",
    uses = { ImageMapper.class, CategoryBasicMapper.class, ReviewDetailMapper.class }
)
public interface ProductDetailMapper {

    ProductDetailDTO toDTO(Product product);

    List<ProductDetailDTO> toDTOs(Collection<Product> products);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toDomain(ProductDetailDTO dto);
}

