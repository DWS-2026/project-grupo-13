package com.example.demo.dto.mapper;

import com.example.demo.Model.OrderItem;
import com.example.demo.dto.OrderItemDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemDetailMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nombre", target = "productName")
    @Mapping(expression = "java(\"/image/\" + orderItem.getProduct().getImage().getId())", target = "imageUrl")
    @Mapping(source = "cantidad", target = "cantidad")
    @Mapping(source = "precio", target = "precio")
    @Mapping(expression = "java(orderItem.getPrecio() * orderItem.getCantidad())", target = "subtotal")
    OrderItemDetailDTO toDTO(OrderItem orderItem);
}
