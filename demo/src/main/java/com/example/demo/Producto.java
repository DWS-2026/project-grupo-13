package com.example.demo;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String imagen;
    private String categoria;

    public Producto(int id, String nombre, double precio, String descripcion, String imagen, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public int getId() {return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getCategoria() { return categoria; }
}


