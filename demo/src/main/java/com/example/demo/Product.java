package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private double precio;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    private Image image;

    public Product() {} // Para JPA

    // Constructor SIN imagen
    public Product(String nombre, double precio, String descripcion, Category category) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.category = category;
    }

    // Constructor CON imagen
    public Product(String nombre, double precio, String descripcion, Category category, Image image) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.category = category;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public Category getCategory() { return category; }
    public Image getImage() { return image; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCategory(Category category) { this.category = category; }
    public void setImage(Image image) { this.image = image; }
}

