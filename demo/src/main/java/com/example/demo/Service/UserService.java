package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.example.demo.Model.Document;
import com.example.demo.Model.Image;
import com.example.demo.Model.User;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.DocumentRepository;
import com.example.demo.Service.DocumentService;


import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    // Métodos básicos
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
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

    // 🔹 Métodos correctos para gestionar la imagen de perfil
    public User addImageToUser(Long id, Image image) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setProfileImage(image);
        userRepository.save(user);

        return user;
    }

    public User removeImageFromUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Image image = user.getProfileImage();
        user.setProfileImage(null);
        userRepository.save(user);

        if (image != null) {
            imageRepository.delete(image);
        }

        return user;
    }

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

}


    






