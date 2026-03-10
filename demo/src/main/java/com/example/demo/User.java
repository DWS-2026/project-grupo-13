package com.example.demo;

// Necesaria para el tipo de dato de fecha
import java.time.LocalDate; 
import org.springframework.format.annotation.DateTimeFormat;

public class User {
    private String name;
    private String surname; // Corregido: no es 'surename'
    private String email;
    private String nickname;
    private String password;

    // AÑADIMOS EL CAMPO DE FECHA DE NACIMIENTO
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Obligatorio para formatear la fecha que envía el HTML
    private LocalDate birthDate;

    // 1. CONSTRUCTOR VACÍO OBLIGATORIO para el framework
    public User() {
    }

    // Constructor con todos los parámetros
    public User(String name, String surname, String email, String nickname, String password, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.birthDate = birthDate;
    }

    // 2. GETTERS Y SETTERS OBLIGATORIOS (Hechos automáticamente con VS Code)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}