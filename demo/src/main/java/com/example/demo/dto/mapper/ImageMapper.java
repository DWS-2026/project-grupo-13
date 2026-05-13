package com.example.demo.dto.mapper;
import com.example.demo.Model.Image;
import com.example.demo.dto.ImageDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    
    ImageDTO toDTO(Image image);

}
