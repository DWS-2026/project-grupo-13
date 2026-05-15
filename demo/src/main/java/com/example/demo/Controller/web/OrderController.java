package com.example.demo.Controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // all user orders
    @GetMapping("/mis-pedidos")
    public String misPedidos(Authentication auth, Model model) {

        String nickname = auth.getName();
        User user = userService.findByNickname(nickname);

        if (user == null) {
            return "redirect:/Login";
        }

        List<Order> pedidos = orderService.findByUser(user);

        for (Order o : pedidos) {
            double total = o.getItems().stream()
                    .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                    .sum();
            o.setTotal(total);
        }

        model.addAttribute("pedidos", pedidos);

        return "OrderHistory";
    }

    // one specific order from user
    @GetMapping("/pedido/{id}")
    public String verPedido(@PathVariable Long id, Authentication auth, Model model) {

        if (auth == null) {
            return "redirect:/Login";
        }

        Order pedido = orderService.findByIdForUser(id);

        double total = pedido.getItems().stream()
                .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                .sum();

        model.addAttribute("pedido", pedido);
        model.addAttribute("total", total);

        return "OrderDetail";
    }

    // create an order
    @PostMapping("/pagar")
    public String pagar(Authentication auth) {

        orderService.createOrderFromCart();

        return "redirect:/mis-pedidos";
    }
}
