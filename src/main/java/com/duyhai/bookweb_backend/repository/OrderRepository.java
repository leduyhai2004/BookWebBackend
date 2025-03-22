package com.duyhai.bookweb_backend.repository;


import com.duyhai.bookweb_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
