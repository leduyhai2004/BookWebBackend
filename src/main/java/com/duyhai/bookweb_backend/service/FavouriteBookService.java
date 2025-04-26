package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.dto.request.BookDTO;
import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.FavouriteBook;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.repository.FavouriteBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(this.favouriteBookRepository.existsByBookAndUser(favBook, user)) {
            throw new RuntimeException("Favourite book already exists");
        }
        FavouriteBook fav = new FavouriteBook();
        if(user != null){
            fav.setUser(user);
            fav.setBook(favBook);
        }
        return favouriteBookRepository.save(fav);
    }

    public List<BookDTO> fetchAllFavouriteBooksOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<FavouriteBook> favouriteBookList = this.favouriteBookRepository.findByUser(user);
        List<BookDTO> bookList = new ArrayList<>();
        favouriteBookList.forEach(fav -> {
            if (fav.getUser().getId() == user.getId()) {
                Book book = fav.getBook();
                String imgURL = book.getImageList().get(0).getDataImage();
                BookDTO bookDTO = BookDTO.builder()
                        .bookId(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .ISBN(book.getISBN())
                        .quantity(book.getQuantity())
                        .rating(book.getRating())
                        .priceOrigin(book.getPriceOrigin())
                        .priceSell(book.getPriceSell())
                        .imageURL(imgURL)
                        .build();
                bookList.add(bookDTO);
            }
        });
        return bookList;
    }



    public List<FavouriteBook> fetchFavouriteBooksByUser(User user) {
        return favouriteBookRepository.findByUser(user);
    }

    public boolean isBookFavouritedByUser(Book book, User user) {
        return favouriteBookRepository.existsByBookAndUser(book, user);
    }

    @Transactional
    public void removeFavouriteBook(int bookId) {
//        Book book = this.bookService.handleGetBookById(bookId);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user = userService.findByUsername(username);
//        favouriteBookRepository.deleteByBookAndUser(book, user);
        favouriteBookRepository.deleteByBook_Id(bookId);
    }
} 