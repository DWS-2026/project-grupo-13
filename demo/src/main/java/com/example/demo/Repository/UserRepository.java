package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.User;

import java.util.List;
//import com.example.demo.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);

    User findByNickname(String nickname);
}


