package com.example.demo.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

// Necesaria para el tipo de dato de fecha
import java.time.LocalDate; 
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;
    
    



    private String name;
    private String surname;
    @Column(unique = true)
    private String email;

    // Username para Spring Security
    @Column(unique = true)
    private String nickname;

    // Contraseña cifrada con BCrypt
    private String encodedPassword;

    // Lista de roles
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public User() {}

    public User(String name, String surname, String email, String nickname,
            String encodedPassword, String role, LocalDate birthDate) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.nickname = nickname;
        this.encodedPassword = encodedPassword;
        this.role = role;
        this.birthDate = birthDate;
    }


    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEncodedPassword() { return encodedPassword; }
    public void setEncodedPassword(String encodedPassword) { this.encodedPassword = encodedPassword; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Image getProfileImage() {return profileImage;}
    public void setProfileImage(Image profileImage) {this.profileImage = profileImage;}

    //For AdminUserDetails
    public boolean isAdmin() {
        return role != null && (role.equals("ADMIN") || role.equals("ROLE_ADMIN"));
    }


}
