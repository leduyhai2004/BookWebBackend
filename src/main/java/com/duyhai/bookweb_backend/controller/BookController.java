package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.dto.request.BookDTO;
import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.Image;
import com.duyhai.bookweb_backend.repository.BookRepository;
import com.duyhai.bookweb_backend.repository.ImageRepository;
import com.duyhai.bookweb_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    // This endpoint accepts a JSON payload with book and nested images
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.handleAddBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        this.bookService.handleDeleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted book");
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id, @RequestBody Book bookReq) {
        int bookId = Integer.parseInt(id);
        Book updatedBook = this.bookService.handleUpdateBook(bookId,bookReq);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
}
