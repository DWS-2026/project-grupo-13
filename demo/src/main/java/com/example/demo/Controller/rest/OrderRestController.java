package com.example.demo.Controller.rest;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.Service.OrderService;
import com.example.demo.dto.OrderBasicDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.OrderBasicMapper;
import com.example.demo.dto.OrderDetailMapper;
import com.example.demo.Model.Order;
import com.example.demo.Repository.OrderRepository;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderBasicMapper orderBasicMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderRepository orderRepository;

    //show all orders in the DB
    @GetMapping("/")
    public Page<OrderBasicDTO> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderBasicMapper::toDTO);
    }

    //show one detailed order
    @GetMapping("/{id}")
    public OrderDetailDTO getOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order == null) {
            throw new NoSuchElementException();
        }
        return orderDetailMapper.toDTO(order);
    }

    //create a new order
    @PostMapping("/")
    public ResponseEntity<OrderDetailDTO> createOrder(@RequestBody OrderDetailDTO orderDetailDTO) {

        Order order = orderDetailMapper.toDomain(orderDetailDTO);

        order = orderService.save(order);

        orderDetailDTO = orderDetailMapper.toDTO(order);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(orderDetailDTO.id()).toUri();

        return ResponseEntity.created(location).body(orderDetailDTO);
    }

}