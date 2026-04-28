package com.example.demo.dto;

import com.example.demo.Model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderBasicMapper {


    @Mapping(source = "user.name", target = "user") 
    OrderBasicDTO toDTO(Order order);

    List<OrderBasicDTO> toDTOs(Collection<Order> orders);
}