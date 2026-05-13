package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;


public record OrderItemDetailDTO(
    Long id,
    Long productId,
    String productName,
    String imageUrl,
    int cantidad,
    double precio,
    double subtotal
) {}

