package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "delivery-methods")
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Integer> {
}
