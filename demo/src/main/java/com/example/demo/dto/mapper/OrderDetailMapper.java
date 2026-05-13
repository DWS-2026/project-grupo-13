package com.example.demo.dto.mapper;

import com.example.demo.Model.Order;
import com.example.demo.dto.OrderDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailDTO toDTO(Order order);

    @Mapping(target = "items", ignore = false) 
    Order toDomain(OrderDetailDTO dto);
}