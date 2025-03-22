package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "images")
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
