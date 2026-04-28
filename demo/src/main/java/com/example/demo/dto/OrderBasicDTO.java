package com.example.demo.dto;

import java.time.LocalDateTime;

public record OrderBasicDTO(Long id, String user, LocalDateTime fecha) {
}