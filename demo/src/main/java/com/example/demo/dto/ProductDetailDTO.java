package com.example.demo.dto;

import com.example.demo.Model.Category;

public record ProductDetailDTO(int id, 
    String nombre, 
    double precio, 
    String descripcion, 
    CategoryBasicDTO category, 
    ImageDTO image) {
    
}
