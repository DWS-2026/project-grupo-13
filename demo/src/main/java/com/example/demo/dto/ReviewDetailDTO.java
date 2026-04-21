package com.example.demo.dto;

import java.time.LocalDate;

public record ReviewDetailDTO (
    long id,
    String usuario,
    int estrellas,
    String comentario,
    LocalDate fecha
    
) {}

