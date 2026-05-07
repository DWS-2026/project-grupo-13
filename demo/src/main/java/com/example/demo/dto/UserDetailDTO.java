package com.example.demo.dto;

import java.time.LocalDate; 

public record UserDetailDTO (
    int id,
    ImageDTO profileImage,
    String name,
    String surname,
    String email,
    String nickname,
    String role,
    LocalDate birthDate,
    DocumentDTO dni

) {}
