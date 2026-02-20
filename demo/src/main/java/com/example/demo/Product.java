package com.example.demo;

public class Product {
    private String name;
    private String description;
    private float price;
    private String category;

    public Product (String name, String description, float price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; } 
    public String getDescription() { return description; } 
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}
