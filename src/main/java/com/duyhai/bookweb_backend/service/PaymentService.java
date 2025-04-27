package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Payment;
import com.duyhai.bookweb_backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public Payment getPaymentById(int id) {
        return  paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public List<String> fetchAllPaymentName() {
        List<Payment> paymentList = paymentRepository.findAll();
        List<String> nameList = paymentList.stream().map(payment -> {
            return payment.getNameOfPayment();
        }).toList();
        return nameList;
    }
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    public void deletePaymentById(int id) {
        paymentRepository.deleteById(id);
    }
    public List<Payment> fetchAllPayments() {
        return paymentRepository.findAll();
    }
}
