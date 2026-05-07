package com.example.demo.dto;

import java.time.LocalDate; 

public record UserCreateDTO(
    String name,
    String surname,
    String email,
    String nickname,
    String password,
    LocalDate birthDate
) {}

