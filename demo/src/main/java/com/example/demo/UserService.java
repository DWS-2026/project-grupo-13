package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.User;
import com.example.demo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Guardar usuario
    public User save(User user) {
        return userRepository.save(user);
    }

    // Buscar todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Buscar por ID
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    // Buscar por email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Eliminar por ID
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
