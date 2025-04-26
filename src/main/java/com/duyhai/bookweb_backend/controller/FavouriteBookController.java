package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.dto.request.BookDTO;
import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.FavouriteBook;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.service.FavouriteBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourite-books")
public class FavouriteBookController {
    private final FavouriteBookService favouriteBookService;

    public FavouriteBookController(FavouriteBookService favouriteBookService) {
        this.favouriteBookService = favouriteBookService;
    }

    @GetMapping
    public ResponseEntity<List<FavouriteBook>> getAllFavouriteBooks() {
        List<FavouriteBook> favouriteBookList = this.favouriteBookService.fetchAllFavouriteBooks();
        if (favouriteBookList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favouriteBookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavouriteBook> getFavouriteBookById(@PathVariable int id) {
        return new ResponseEntity<>(this.favouriteBookService.fetchFavouriteBookById(id), HttpStatus.OK);
    }

    @PostMapping("add/{bookId}")
    public ResponseEntity<Void> addFavouriteBook(@PathVariable int bookId) {
        this.favouriteBookService.saveFavouriteBook(bookId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllFavouriteBooksOfAUser() {
        List<BookDTO> favouriteBooks = this.favouriteBookService.fetchAllFavouriteBooksOfUser();
        if (favouriteBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favouriteBooks);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavouriteBook>> getFavouriteBooksByUser(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);
        List<FavouriteBook> favouriteBooks = this.favouriteBookService.fetchFavouriteBooksByUser(user);
        if (favouriteBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favouriteBooks);
    }

    @GetMapping("/check-favourite")
    public ResponseEntity<Boolean> checkIfBookIsFavourited(
            @RequestParam int bookId,
            @RequestParam int userId) {
        Book book = new Book();
        book.setId(bookId);
        User user = new User();
        user.setId(userId);
        return ResponseEntity.ok(this.favouriteBookService.isBookFavouritedByUser(book, user));
    }

    @DeleteMapping("/remove-favourite/{bookId}")
    public ResponseEntity<Void> removeFavouriteBook(@PathVariable("bookId") int bookId) {
        //int id = Integer.parseInt(bookId);
        this.favouriteBookService.removeFavouriteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
} 