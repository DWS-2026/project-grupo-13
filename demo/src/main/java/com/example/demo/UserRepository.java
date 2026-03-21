package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//import com.example.demo.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);

    User findByNickname(String nickname);
}


