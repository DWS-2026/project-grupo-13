package com.example.demo.dto;

public record OrderItemDetailDTO(
    Long id,
    Long productId,
    String productName, 
    String imageUrl,    
    int cantidad,
    double precio,
    double subtotal     
) {
}