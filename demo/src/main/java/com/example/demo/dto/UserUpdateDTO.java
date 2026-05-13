package com.example.demo.dto;

import java.time.LocalDate; 

public record UserUpdateDTO(
        String name,
        String surname,
        String email,
        LocalDate birthDate
) {}


