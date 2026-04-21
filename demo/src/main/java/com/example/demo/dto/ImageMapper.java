package com.example.demo.dto;
import com.example.demo.Model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    
    ImageDTO toDTO(Image image);

}
