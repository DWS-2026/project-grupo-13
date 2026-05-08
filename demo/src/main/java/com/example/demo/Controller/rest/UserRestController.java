package com.example.demo.Controller.rest;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ImageMapper;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.UserBasicDTO;
import com.example.demo.dto.UserBasicMapper;
import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserDetailMapper;
import com.example.demo.Model.Image;
import com.example.demo.Model.Product;
import com.example.demo.Service.ImageService;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserDetailDTO;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private UserRepository userRepository;

    //show a list of all users
    @GetMapping("/")
    public Page<UserBasicDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userBasicMapper::toDTO);
    }

    //show one detailed user
    @GetMapping("/{id}")
    public UserDetailDTO getUser(@PathVariable Long id) {
        return userDetailMapper.toDTO(userService.findById(id));
    }

    //create new user
    @PostMapping("/")
    public ResponseEntity<UserDetailDTO> createUser(@RequestBody UserCreateDTO dto) {
System.out.println(">>> ENTRA EN EL CONTROLLER createUser()");
    System.out.println(">>> DTO RECIBIDO: " + dto);
        UserDetailDTO created = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    
    example:

    {
    "name": "Lucía",
    "surname": "Martínez",
    "email": "lucia@example.com",
    "nickname": "lmartinez",
    "password": "ClaveUltraSegura456",
    "birthDate": "1998-11-03"
    }

    */


    //delete a user
    @DeleteMapping("/{id}")
    public UserDetailDTO deleteUser(@PathVariable Long id) {

        User user = userService.findById(id);

        userService.deleteById(id);

        return userDetailMapper.toDTO(user);

    }

    //replace a user
    @PutMapping("/{id}")
    public UserDetailDTO replaceUser(@PathVariable Long id, @RequestBody UserDetailDTO newUserDTO) {

        if (userService.existsById(id)) {

            User newUser = userDetailMapper.toDomain(newUserDTO);

            newUser.setId(id);
            userService.save(newUser);

            return userDetailMapper.toDTO(newUser);

        } else {
            throw new NoSuchElementException();
        }

    }
    
    
    @PostMapping("/{id}/image")
    public ResponseEntity<ImageDTO> uploadProfileImage(@PathVariable Long id,
                                                       @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile)
            throws IOException {

        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        Image image = imageService.createImage(imageFile.getInputStream());
        userService.addImageToUser(id, image);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/{imageId}/media")
                .buildAndExpand(image.getId())
                .toUri();

        return ResponseEntity.created(location).body(imageMapper.toDTO(image));
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<ImageDTO> deleteProfileImage(@PathVariable Long id) {
        User user = userService.findById(id);
        Image image = user.getProfileImage();
        userService.removeImageFromUser(id);

        if (image != null) {
            imageService.deleteImage(image.getId());
            return ResponseEntity.ok(imageMapper.toDTO(image));
        }

        return ResponseEntity.noContent().build();
    }

}
