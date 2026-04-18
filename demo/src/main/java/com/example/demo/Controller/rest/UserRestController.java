package com.example.demo.Controller.rest;
import com.example.demo.dto.UserBasicDTO;
import com.example.demo.dto.UserBasicMapper;
import com.example.demo.Service.UserService;

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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserBasicMapper userBasicMapper;

    @GetMapping("/")
    public List<UserBasicDTO> getUsers() {
        return userService.findAll().stream().map(userBasicMapper::toDTO).toList();
    }

}
