package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.entity.DeliveryMethod;
import com.duyhai.bookweb_backend.entity.Payment;
import com.duyhai.bookweb_backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayment() {
        List<Payment> paymentList = paymentService.fetchAllPayments();
        if (paymentList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paymentList);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllPaymentName() {
        List<String> paymentList = paymentService.fetchAllPaymentName();
        if (paymentList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paymentList);
    }
}
