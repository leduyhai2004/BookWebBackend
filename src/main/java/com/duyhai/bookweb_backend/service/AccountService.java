package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Notification;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> registerUser(User user) {
        //kiem tra ten dang nhap
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new Notification("Username already exists"));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new Notification("Email already exists"));
        }
        // ma hoa mat khau
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);

        // luu vao db
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok("Registered user successfully");
    }
}
