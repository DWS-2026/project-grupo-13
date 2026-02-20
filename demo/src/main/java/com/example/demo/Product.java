package com.example.demo;

public class Product {
    private String name;
    private String description;
    private float price;

    public Product (String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getNombre() { return name; } 
    public String getDescripcion() { return description; } 
    public double getPrecio() { return price; }
}
