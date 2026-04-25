package com.example.demo.dto;
import com.example.demo.Model.Category;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CategoryBasicMapper {
    
    CategoryBasicDTO toDTO (Category category);

    Category toDomain(CategoryBasicDTO categoryBasicDTO);

}
