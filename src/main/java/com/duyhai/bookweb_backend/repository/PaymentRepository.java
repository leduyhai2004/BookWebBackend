package com.duyhai.bookweb_backend.repository;

import com.duyhai.bookweb_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "payments")
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
