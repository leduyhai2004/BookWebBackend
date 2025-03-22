package com.duyhai.bookweb_backend.repository;
import com.duyhai.bookweb_backend.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "order-details")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
