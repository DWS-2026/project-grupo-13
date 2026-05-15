package com.example.demo.dto.mapper;

import com.example.demo.Model.Order;
import com.example.demo.dto.OrderDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemDetailMapper.class)
public interface OrderDetailMapper {

    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "total", expression = "java(order.getItems().stream().mapToDouble(i -> i.getCantidad() * i.getPrecio()).sum())")
    OrderDetailDTO toDTO(Order order);
    
}
