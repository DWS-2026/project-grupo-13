package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;


public record OrderDetailDTO(
    Long id,
    LocalDateTime fecha,
    double total,
    String nickname, // comes from user
    String email,    // comes from user
    List<OrderItemDetailDTO> items 
) {}