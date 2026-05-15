package com.example.demo.Controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.mapper.OrderDetailMapper;
import com.example.demo.Model.CartItem;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Service.CartService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.CartItemDTO;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<CartItem>> getCart() {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cartService.getCartItems(nickname));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addToCart(
            @PathVariable int productId,
            @RequestParam(defaultValue = "1") int cantidad) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        Product p = productService.findById(productId);

        CartItem item = new CartItem(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                cantidad
        );

        cartService.addItem(nickname, item);

        return ResponseEntity.ok().build();
    }

}
