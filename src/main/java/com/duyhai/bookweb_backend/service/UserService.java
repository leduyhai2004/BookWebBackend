package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
}
