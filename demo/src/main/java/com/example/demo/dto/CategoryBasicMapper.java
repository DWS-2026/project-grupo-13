package com.example.demo.dto;
import com.example.demo.Model.Category;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring")
public interface CategoryBasicMapper {
    
    CategoryBasicDTO toDTO (Category category);

    List<CategoryBasicDTO> toDTOs (Collection<Category> categories);

    Category toDomain (CategoryBasicDTO categoryBasicDTO);

}
