package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "ratings")
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
