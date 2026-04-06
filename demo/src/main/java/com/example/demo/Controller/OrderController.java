package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;

import org.springframework.security.web.csrf.CsrfToken;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/mis-pedidos")
    public String misPedidos(Authentication auth, Model model) {

        String nickname = auth.getName();
        User user = userService.findByNickname(nickname);

        List<Order> pedidos = orderService.findByUser(user);

        // get CSRF token for the forms
        CsrfToken token = (CsrfToken) ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute("_csrf");

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("token", token.getToken());

        for (Order o : pedidos) {
            double total = o.getItems().stream()
                .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                .sum();
            o.setTotal(total);
        }

        return "OrderHistory";
    }


    @GetMapping("/pedido/{id}")
    public String verPedido(@PathVariable Long id, Authentication auth, Model model) {

        Order pedido = orderService.findById(id);

        if (pedido == null) {
            return "redirect:/mis-pedidos";
        }

        String nickname = auth.getName();
        if (!pedido.getUser().getNickname().equals(nickname)) {
            return "redirect:/mis-pedidos";
        }

        double total = pedido.getItems().stream()
            .mapToDouble(i -> i.getPrecio() * i.getCantidad())
            .sum();

        model.addAttribute("pedido", pedido);
        model.addAttribute("total", total);


        return "OrderDetail";
    }
}

