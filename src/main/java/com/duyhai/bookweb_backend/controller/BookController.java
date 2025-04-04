package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.dto.request.BookDTO;
import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.Image;
import com.duyhai.bookweb_backend.repository.BookRepository;
import com.duyhai.bookweb_backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // This endpoint accepts a JSON payload with book and nested images
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // Set the book reference for each image so that the bidirectional relationship is maintained.
        if (book.getImageList() != null) {
            book.getImageList().forEach(image -> image.setBook(book));
        }
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
}
