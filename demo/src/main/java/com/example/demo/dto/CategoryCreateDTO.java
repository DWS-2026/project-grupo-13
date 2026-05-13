package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public record CategoryCreateDTO(
        String name,
        MultipartFile image
) {}

