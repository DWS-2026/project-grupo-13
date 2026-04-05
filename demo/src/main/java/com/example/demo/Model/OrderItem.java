package com.example.demo.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int cantidad;

    private double precio; // precio en el momento de la compra

    @Transient
    private double total;

    public OrderItem() {}

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    public void setId(Long id) { this.id = id; }
    public void setOrder(Order order) { this.order = order; }
    public void setProduct(Product product) { this.product = product; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Transient
    public double getSubtotal() {
        return precio * cantidad;
    }

    @Transient
    public String getImageUrl() {
        return "/image/" + product.getImage().getId();
    }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

}

