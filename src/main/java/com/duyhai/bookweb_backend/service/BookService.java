package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.Image;
import com.duyhai.bookweb_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public void handleDeleteBook(int id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            bookRepository.deleteById(id);
        }

    }

    public Book handleAddBook(Book book){
        // Set the book reference for each image so that the bidirectional relationship is maintained.
        if (book.getImageList() != null) {
            book.getImageList().forEach(image -> image.setBook(book));
        }
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public Book handleUpdateBook(int id,Book bookReq){
        Book currentBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));;
        if(currentBook != null){
            currentBook.setName(bookReq.getName());
            currentBook.setAuthor(bookReq.getAuthor());
            currentBook.setDescription(bookReq.getDescription());
            List<Image> newImages = new ArrayList<>();
            if (bookReq.getImageList() != null) {
                bookReq.getImageList().forEach(image ->{
                    image.setBook(currentBook);
                    newImages.add(image);
                });
            }
            currentBook.setImageList(newImages);
            currentBook.setISBN(bookReq.getISBN());
            currentBook.setDescription(bookReq.getDescription());
            currentBook.setPriceOrigin(bookReq.getPriceOrigin());
            currentBook.setPriceSell(bookReq.getPriceSell());
            currentBook.setQuantity(bookReq.getQuantity());
        }
        this.bookRepository.save(currentBook);
        return currentBook;
    }

    public Book handleGetBookById(int id){
        return this.bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
}
