package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody User user){
        ResponseEntity<?> responseEntity = accountService.registerUser(user);
        return responseEntity;
    }
}
