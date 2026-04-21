package com.example.demo.dto;

import com.example.demo.Model.User;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Collection;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserDetailMapper {
    
    UserDetailDTO toDTO (User user);

    @Mapping(target = "profileImage", ignore = true)
    User toDomain(UserDetailDTO dto);

}
