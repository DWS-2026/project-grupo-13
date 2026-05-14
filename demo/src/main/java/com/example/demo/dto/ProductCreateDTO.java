package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProductCreateDTO(
    String nombre,
    double precio,
    String descripcion,
    boolean promotion,
    Double precioOriginal,
    Double precioOferta,
    Long categoryId,
    MultipartFile image
) {}
