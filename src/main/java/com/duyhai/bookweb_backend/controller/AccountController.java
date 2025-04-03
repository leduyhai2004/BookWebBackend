package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.dto.request.LoginRequest;
import com.duyhai.bookweb_backend.dto.response.JwtResponse;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.service.AccountService;
import com.duyhai.bookweb_backend.service.JwtService;
import com.duyhai.bookweb_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("*") // test on postman
public class AccountController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody User user){
        ResponseEntity<?> responseEntity = accountService.registerUser(user);
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginReq){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
            // tao JWT
            if(authentication.isAuthenticated()){
                final String jwt = jwtService.generateToken(loginReq.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @GetMapping("/active")
    public ResponseEntity<?> activeAnAccount(@RequestParam String email, @RequestParam String idOfActivation){
        ResponseEntity<?> responseEntity = accountService.activeAccount(email, idOfActivation);
        return responseEntity;
    }



}
