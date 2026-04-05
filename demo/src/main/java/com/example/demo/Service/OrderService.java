package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Guardar un pedido
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    // Obtener todos los pedidos de un usuario
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // Obtener un pedido por ID (opcional)
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Obtener todos los pedidos (solo admin)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}

