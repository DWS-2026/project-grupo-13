package com.example.demo.dto;

public record OrderItemBasicDTO(
    Long id,
    int cantidad,
    double precio,
    double subtotal
) {
}