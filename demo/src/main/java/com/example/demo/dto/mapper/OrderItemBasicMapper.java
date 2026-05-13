package com.example.demo.dto.mapper;

import com.example.demo.Model.OrderItem;
import com.example.demo.dto.OrderItemBasicDTO;

import org.mapstruct.Mapper;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemBasicMapper {

    OrderItemBasicDTO toDTO(OrderItem orderItem);

    List<OrderItemBasicDTO> toDTOs(Collection<OrderItem> orderItems);
}