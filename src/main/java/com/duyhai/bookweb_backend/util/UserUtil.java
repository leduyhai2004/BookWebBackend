package com.duyhai.bookweb_backend.util;

import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    private final UserService userService;
    public UserUtil(UserService userService) {
        this.userService = userService;
    }
    public boolean isUserLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return false;
        }
        return true;
    }
    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        return user;
    }
}
