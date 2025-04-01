package com.duyhai.bookweb_backend.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendMessage(String from,String to, String subject, String text);
}
