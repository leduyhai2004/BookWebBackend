package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Notification;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

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

        //Gan va gui thong tin kich hoat
        user.setIdOfActivation(createIdActivation());
        user.setActive(false);

        // luu vao db
        User savedUser = userRepository.save(user);

        //sau do gui email cho nguoi dung de kich hoat tai khoan
        sendEmailActivation(user.getEmail(),user.getIdOfActivation());

        return ResponseEntity.ok("Registered user successfully");
    }

    private String createIdActivation(){
        //tao ma ngau nhien
        return UUID.randomUUID().toString();
    }
    private void sendEmailActivation(String email, String idOfActivation) {
        String subject = "Active your account at DHBooks <3";
        String textBody = "Use this code to activate your account: '" +email+"' at DHBooks</br><html><body><h1>"+idOfActivation+"</h1></body></h1>";
        textBody += "</br> Click here to active: ";
        String url = "http://localhost:3000/active?email="+email+"&idOfActivation="+idOfActivation;
        textBody += "<a href="+url+">Activate your account</a>";
        emailService.sendMessage("bighand272004@gmail.com",email,subject,textBody);
    }

    public ResponseEntity<?> activeAccount(String email, String idOfActivation) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return ResponseEntity.badRequest().body(new Notification("User not found"));
        }
        if(user.isActive()){
            return ResponseEntity.badRequest().body(new Notification("Account is already activated"));
        }
        if(user.getIdOfActivation().equals(idOfActivation)){
            user.setActive(true);
            userRepository.save(user);
            return ResponseEntity.ok().body(new Notification("Account is already activated"));
        }else {
            return ResponseEntity.badRequest().body(new Notification("The code is not correct"));
        }

    }




}
