package com.example.demo.Controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.OrderService;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.mapper.OrderDetailMapper;
import com.example.demo.Model.Order;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.findByIdForUser(id);
            return ResponseEntity.ok(orderDetailMapper.toDTO(order));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("No puedes ver este pedido");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder() {
        try {
            Order order = orderService.createOrderFromCart();
            return ResponseEntity.ok(orderDetailMapper.toDTO(order));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteByIdForUser(id);
            return ResponseEntity.ok("Pedido eliminado");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("No puedes borrar este pedido");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
    }
}
