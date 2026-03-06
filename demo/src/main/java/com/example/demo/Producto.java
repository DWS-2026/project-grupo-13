package com.example.demo;

public class Producto {
    private String nombre;
    private double precio;
    private String descripcion;
    private String imagen;
    private String categoria;

    public Producto(String nombre, double precio, String descripcion, String imagen, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getCategoria() { return categoria; }
}


