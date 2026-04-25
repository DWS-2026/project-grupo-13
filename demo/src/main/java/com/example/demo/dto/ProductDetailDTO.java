package com.example.demo.dto;

import java.util.List;


public record ProductDetailDTO(
    int id, 
    String nombre, 
    double precio, 
    String descripcion, 
    CategoryBasicDTO category, 
    ImageDTO image,
    List<ReviewDetailDTO> reviews

) {}
