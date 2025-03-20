package com.kaivix.authservice.service;

import com.kaivix.authservice.model.User;
import com.kaivix.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
