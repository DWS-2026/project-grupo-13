package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public User save(User user) {
        return userRepository.save(user);
    }

    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> findByNameContainingIgnoreCase(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public User findByNickname(String nickname) {
    return userRepository.findByNickname(nickname);
}


}
