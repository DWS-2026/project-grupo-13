package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "REVIEWS")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private int estrellas;

    @Column(columnDefinition = "MEDIUMTEXT")
    @Size (max = 5000)
    private String comentario;
    private LocalDate fecha;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Transient 
    private boolean canDelete;

    public Review() {
        this.fecha = LocalDate.now();
    }

    public Review(String usuario, int estrellas, String comentario, LocalDate fecha, Product product) {
        this.usuario = usuario;
        this.estrellas = estrellas;
        this.setComentario(comentario);
        this.fecha = fecha;
        this.product = product;
    }

    
    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isCanDelete() { return canDelete; }
    public void setCanDelete(boolean canDelete) { this.canDelete = canDelete; }
}