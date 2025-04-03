package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
}
