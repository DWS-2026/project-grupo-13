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

    
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}

