package com.example.demo.dto;
import org.springframework.stereotype.Component;
import com.example.demo.Model.User;

@Component
public class UserBasicMapper {
    
    public UserBasicDTO toDTO (User user) {
        return new UserBasicDTO(user.getId(), user.getNickname(), user.getEmail(), user.getRole());
    }

}
