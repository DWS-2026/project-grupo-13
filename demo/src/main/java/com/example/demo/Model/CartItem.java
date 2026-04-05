package com.example.demo.Model;

public class CartItem {
    private int productId;
    private String nombre;
    private String imagen;
    private double precio;
    private int cantidad;

    public CartItem() {}

    public CartItem(int productId, String nombre, String imagen, double precio, int cantidad) {
        this.productId = productId;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
