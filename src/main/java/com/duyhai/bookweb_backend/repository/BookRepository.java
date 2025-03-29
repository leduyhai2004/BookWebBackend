package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

//http://localhost:8080/books/search
@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByNameContainingIgnoreCase(@RequestParam("name") String name, Pageable pageable);
    Page<Book> findByTypeList_Id(@RequestParam("type_id") int type_id, Pageable pageable);
    Page<Book> findByNameContainingIgnoreCaseAndTypeList_Id( @RequestParam("name") String name, @RequestParam("type_id") int type_id, Pageable pageable);
}
