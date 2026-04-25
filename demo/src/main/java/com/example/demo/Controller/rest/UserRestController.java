package com.example.demo.Controller.rest;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.UserBasicDTO;
import com.example.demo.dto.UserBasicMapper;
import com.example.demo.dto.UserDetailMapper;
import com.example.demo.Model.Product;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserDetailDTO;
import com.example.demo.Model.User;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    //show a list of all users
    @GetMapping("/")
    public List<UserBasicDTO> getUsers() {
        return userBasicMapper.toDTOs(userService.findAll());
    }

    //show one detailed user
    @GetMapping("/{id}")
    public UserDetailDTO getUser(@PathVariable int id) {
        return userDetailMapper.toDTO(userService.findById(id));
    }

    //create new user
    @PostMapping("/")
    public ResponseEntity<UserDetailDTO> createProduct (@RequestBody UserDetailDTO userDetailDTO) {

        User user = userDetailMapper.toDomain(userDetailDTO);

        user = userService.createUser(user);

        userDetailDTO = userDetailMapper.toDTO(user);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userDetailDTO.id()).toUri();

        return ResponseEntity.created(location).body(userDetailDTO);
    }

    //delete a user
    @DeleteMapping("/{id}")
    public UserDetailDTO deleteUser(@PathVariable int id) {

        User user = userService.findById(id);

        userService.deleteById(id);

        return userDetailMapper.toDTO(user);

    }

    //replace a user
    @PutMapping("/{id}")
    public UserDetailDTO replaceUser(@PathVariable int id, @RequestBody UserDetailDTO newUserDTO) {

        if (userService.existsById(id)) {

            User newUser = userDetailMapper.toDomain(newUserDTO);

            newUser.setId(id);
            userService.save(newUser);

            return userDetailMapper.toDTO(newUser);

        } else {
            throw new NoSuchElementException();
        }

    }

}
