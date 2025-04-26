package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.Book;
import com.duyhai.bookweb_backend.entity.FavouriteBook;
import com.duyhai.bookweb_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "favourite-books")
public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, Integer> {
    List<FavouriteBook> findByUser(User user);
    boolean existsByBookAndUser(Book book, User user);
    void deleteByBookAndUser(Book book, User user);
    boolean existsByBook_Id(int bookId);
    void deleteByBook_Id(int bookId);
}
