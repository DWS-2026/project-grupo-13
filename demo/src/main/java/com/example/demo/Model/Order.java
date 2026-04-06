package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS") // "ORDER" is reserved in SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public Order() {}

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public User getUser() { return user; }
    public List<OrderItem> getItems() { return items; }

    public void setId(Long id) { this.id = id; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setUser(User user) { this.user = user; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

