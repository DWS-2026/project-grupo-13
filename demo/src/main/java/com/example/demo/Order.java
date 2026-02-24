package com.example.demo;
import java.util.List;

public class Order {
    private int id;
    private User user;
    private double total;
    private List<Product> products;

    public Order(int id, List<Product> products) { this.id = id; this.products = products; this.total = calculateTotal(); }

    private double calculateTotal() { return products.stream() .mapToDouble(Product::getPrice) .sum(); }

    public int getId() { return id; } 
    public List<Product> getProducts() { return products; } 
    public double getTotal() { return total; }

}
