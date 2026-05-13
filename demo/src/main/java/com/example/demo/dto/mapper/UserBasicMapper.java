package com.example.demo.dto.mapper;

import com.example.demo.Model.User;
import com.example.demo.dto.UserBasicDTO;

import org.mapstruct.Mapper;
import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring")
public interface UserBasicMapper {
    
    List<UserBasicDTO> toDTOs(Collection<User> users);

    UserBasicDTO toDTO(User user);

}
