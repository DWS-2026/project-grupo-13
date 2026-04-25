package com.example.demo.dto;


public record ProductDetailDTO(
    int id, 
    String nombre, 
    double precio, 
    String descripcion, 
    CategoryBasicDTO category, 
    ImageDTO image) {
    
}
