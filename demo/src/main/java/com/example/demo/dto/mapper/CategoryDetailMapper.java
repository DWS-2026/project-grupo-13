package com.example.demo.dto.mapper;

import com.example.demo.dto.CategoryDetailDTO;

import com.example.demo.Model.Category;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CategoryDetailMapper {
    
    CategoryDetailDTO toDTO (Category category);

    Category toDomain (CategoryDetailDTO dto);

}
