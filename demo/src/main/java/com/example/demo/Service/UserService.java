package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.IOException;

import com.example.demo.Model.Document;
import com.example.demo.Model.Image;
import com.example.demo.Model.User;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.DocumentRepository;
import com.example.demo.Service.DocumentService;
import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserDetailDTO;
import com.example.demo.dto.mapper.UserDetailMapper;

import com.example.demo.Service.ImageService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailMapper userDetailMapper;


    
   public User save(User user) {

        if (user.getId() == null) {

            if (userRepository.findByNickname(user.getNickname()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname ya existe");
            }

            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ya existe");
            }
        }

        if (user.getEncodedPassword() != null && !user.getEncodedPassword().startsWith("$2a$")) {
            user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByNameContainingIgnoreCase(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }


    //////////////////////////////// UPLOAD AND DELETE PROFILE PICTURES ///////////////////////////////////////////

    public Image uploadProfileImage(Long userId, MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El archivo no puede estar vacío");
        }

        
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByNickname(nickname);

        if (authUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }

        
        boolean isOwner = authUser.getId().equals(userId);
        boolean isAdmin = authUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar este perfil");
        }

        Image image = imageService.createImage(imageFile.getInputStream());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        user.setProfileImage(image);
        userRepository.save(user);

        return image;
    }

        public void deleteProfileImage(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (user.getProfileImage() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene imagen");
        }

        Image image = user.getProfileImage();
        user.setProfileImage(null);
        userRepository.save(user);

        imageService.deleteImage(image.getId());
    }


    //////////////////////////////// UPLOAD AND DELETE PROFILE PICTURES ///////////////////////////////////////////


    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    //for the file
    public ResponseEntity<?> addDniToUser(long userId, MultipartFile file) throws IOException {
         
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticatedUser = userRepository.findByNickname(nickname);

        
        if (authenticatedUser == null || authenticatedUser.getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        
        User user = authenticatedUser;

        
        Document doc = user.getDni();
        if (doc == null) {
            doc = new Document();
            doc.setUser(user);
        }

        doc.setOriginalName(file.getOriginalFilename());

        Document saved = documentRepository.save(doc);

        String path = documentService.saveFile(file, saved.getId());
        saved.setFilePath(path);

        documentRepository.save(saved);

        user.setDni(saved);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public UserDetailDTO createUser(UserCreateDTO dto) {

        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setNickname(dto.nickname());
        user.setBirthDate(dto.birthDate());
        user.setRole("USER");
        user.setEncodedPassword(dto.password()); 

        User saved = save(user); 

        return userDetailMapper.toDTO(saved);
    }

    public User deleteUserIfAuthorised(Long id) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User userAuth = userRepository.findByNickname(nickname);

        if (userAuth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }

        if (!userAuth.getId().equals(id) && !userAuth.getRole().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para borrar este usuario");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);

        return user;
    }


}


    






