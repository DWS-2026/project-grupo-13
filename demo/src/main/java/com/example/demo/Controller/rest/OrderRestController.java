package com.example.demo.Controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.OrderService;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.mapper.OrderDetailMapper;
import com.example.demo.dto.mapper.OrderBasicMapper;
import com.example.demo.Model.Order;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderBasicMapper orderBasicMapper;

    @GetMapping("/")
    public ResponseEntity<?> getOrders() {
        try {
            List<Order> orders = orderService.findAll();
            return ResponseEntity.ok(orderBasicMapper.toDTOs(orders));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay pedidos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.findByIdForUser(id);
            return ResponseEntity.ok(orderDetailMapper.toDTO(order));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes ver este pedido");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder() {
        try {
            Order order = orderService.createOrderFromCart();
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDetailMapper.toDTO(order));
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes borrar este pedido");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }
    }
}
