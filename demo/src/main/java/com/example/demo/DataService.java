package com.example.demo;

import java.util.List;

public class DataService {

    public static List<Category> getCategories() {
        return List.of(
            new Category("Laptops", "Portátiles"),
            new Category("Smartphones", "Móviles"),
            new Category("Tablets", "Tabletas")
        );
    }

    public static List<Product> getProducts() {
        return List.of(
            new Product("MacBook Air", "Ligero y potente", 1299, "Laptops"),
            new Product("Dell XPS 13", "Ultrabook premium", 1499, "Laptops")
        );
    }
}

