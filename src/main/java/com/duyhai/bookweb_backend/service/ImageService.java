package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Image;
import com.duyhai.bookweb_backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void handleDeleteAnImage(int id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        if(image != null){
            this.imageRepository.deleteById(id);
        }
    }
}
