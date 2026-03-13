package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Necesaria para el tipo de dato de fecha
import java.time.LocalDate; 
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String nickname;
    private String password;
    

    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    // necesario para JPA
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

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}