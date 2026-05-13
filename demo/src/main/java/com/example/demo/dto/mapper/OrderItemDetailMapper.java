package com.example.demo.dto.mapper;

import com.example.demo.Model.OrderItem;
import com.example.demo.dto.OrderItemDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemDetailMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nombre", target = "productName") 
    @Mapping(source = "imageUrl", target = "imageUrl")          
    @Mapping(source = "subtotal", target = "subtotal")          
    OrderItemDetailDTO toDTO(OrderItem orderItem);

    List<OrderItemDetailDTO> toDTOs(Collection<OrderItem> orderItems);
}