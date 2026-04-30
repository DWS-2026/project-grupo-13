package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Image;
import com.example.demo.Model.User;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    
    public User save(User user) {
        return userRepository.save(user);
    }

    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    

    public User createUser(User user){

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


    

    // 🔹 Methods for Profile Image
    public User addImageToUser(Long id, Image image) {
        User user = userRepository.findById(id).orElseThrow();
        user.setProfileImage(image);
        userRepository.save(user);
        return user;
    }

    public User removeImageFromUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
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
}
    






