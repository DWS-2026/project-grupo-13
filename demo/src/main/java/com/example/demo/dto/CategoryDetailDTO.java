package com.example.demo.dto;

import java.util.List;

public record CategoryDetailDTO(
    Long id,
    String name,
    List<ProductBasicDTO> products,
    ImageDTO image

) {}
