package com.example.demo.Controller.rest;
import com.example.demo.dto.UserBasicDTO;
import com.example.demo.dto.UserBasicMapper;
import com.example.demo.dto.UserDetailMapper;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserDetailDTO;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @GetMapping("/")
    public List<UserBasicDTO> getUsers() {
        return userBasicMapper.toDTOs(userService.findAll());
    }

    @GetMapping("/{id}")
    public UserDetailDTO getUser(@PathVariable int id) {
        return userDetailMapper.toDTO(userService.findById(id));
    }

}
