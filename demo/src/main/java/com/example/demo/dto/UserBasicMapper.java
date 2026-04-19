package com.example.demo.dto;
import com.example.demo.Model.User;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Collection;

@Mapper (componentModel = "spring")
public interface UserBasicMapper {
    
    List<UserBasicDTO> toDTOs(Collection<User> users);

}
