package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.FavouriteBook;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.repository.FavouriteBookRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteBookService {
    private final FavouriteBookRepository favouriteBookRepository;
    private final UserService userService;
    private final BookService bookService;

    public FavouriteBookService(FavouriteBookRepository favouriteBookRepository,
                                UserService userService,
                                BookService bookService) {
        this.favouriteBookRepository = favouriteBookRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<FavouriteBook> fetchAllFavouriteBooks() {
        return favouriteBookRepository.findAll();
    }

    public FavouriteBook fetchFavouriteBookById(int id) {
        Optional<FavouriteBook> favouriteBook = favouriteBookRepository.findById(id);
        return favouriteBook.orElseThrow(() -> new RuntimeException("Favourite book not found"));
    }

    public FavouriteBook saveFavouriteBook(int bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Book favBook = this.bookService.handleGetBookById(bookId);
        FavouriteBook fav = new FavouriteBook();
        if(user != null){
            fav.setUser(user);
            fav.setBook(favBook);
        }
        return favouriteBookRepository.save(fav);
    }

    public void deleteFavouriteBookById(int id) {
        favouriteBookRepository.deleteById(id);
    }

    public List<FavouriteBook> fetchFavouriteBooksByUser(User user) {
        return favouriteBookRepository.findByUser(user);
    }

    public boolean isBookFavouritedByUser(Book book, User user) {
        return favouriteBookRepository.existsByBookAndUser(book, user);
    }

    public void removeFavouriteBook(Book book, User user) {
        favouriteBookRepository.deleteByBookAndUser(book, user);
    }
} 