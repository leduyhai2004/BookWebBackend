package com.duyhai.bookweb_backend.repository;


import com.duyhai.bookweb_backend.entity.Order;
import com.duyhai.bookweb_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
    boolean existsByOrderIdAndUser(int orderId, User user);
}
