package com.example.demo.dto;

public record CartItemDTO(
        int productId,
        String nombre,
        String imagen,
        double precio,
        int cantidad
) {}

