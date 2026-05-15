package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.CartItem;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    //put all the items on cart on a new order
    public Order createOrderFromCart() {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNickname(nickname);

        if (user == null) {
            throw new AccessDeniedException("Usuario no autenticado");
        }

        List<CartItem> carrito = cartService.getCartItems(nickname);

        if (carrito.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        Order order = new Order();
        order.setUser(user);
        order.setFecha(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();

        for (CartItem c : carrito) {

            /*
            if (c.getCantidad() < 0 || c.getCantidad() > 10) {
                throw new IllegalArgumentException("Cantidad inválida");
            }
            */

            Product p = productService.findById(c.getProductId());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(p);
            item.setCantidad(c.getCantidad());
            item.setPrecio(p.getPrecio());

            items.add(item);
        }

        order.setItems(items);

        Order saved = orderRepository.save(order);

        cartService.clearCart(nickname);

        return saved;
    }

    public Order findByIdForUser(Long id) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNickname(nickname);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("No puedes ver un pedido que no es tuyo");
        }

        return order;
    }

    public void deleteByIdForUser(Long id) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNickname(nickname);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("No puedes borrar un pedido que no es tuyo");
        }

        orderRepository.delete(order);
    }

    public List<Order> findByUser(User user) {
    return orderRepository.findByUser(user);
    }

    public Order save(Order order) {
    return orderRepository.save(order);
    }
}
