package com.example.demo.dto;

public record ProductCreateDTO(
    String nombre,
    double precio,
    String descripcion,
    boolean promotion,
    Double precioOriginal,
    Double precioOferta,
    Long categoryId
) {}
