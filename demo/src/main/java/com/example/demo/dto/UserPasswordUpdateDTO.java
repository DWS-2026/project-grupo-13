package com.example.demo.dto;

public record UserPasswordUpdateDTO(
        String oldPassword,
        String newPassword
) {}

