package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.FavouriteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "favourite-books")
public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, Integer> {

}
