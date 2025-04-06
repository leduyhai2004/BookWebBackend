package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @DeleteMapping("/images/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        this.imageService.handleDeleteAnImage(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted image successfully");
    }
}
