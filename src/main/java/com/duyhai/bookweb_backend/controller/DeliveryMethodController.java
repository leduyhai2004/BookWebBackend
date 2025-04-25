package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.entity.DeliveryMethod;
import com.duyhai.bookweb_backend.service.DeliveryMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-methods")
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;

    public DeliveryMethodController(DeliveryMethodService deliveryMethodService) {
        this.deliveryMethodService = deliveryMethodService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryMethod>> getAllDeliveryMethods() {
        List<DeliveryMethod> deliveryMethodList = deliveryMethodService.fetchAllDeliveryMethods();
        if (deliveryMethodList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliveryMethodList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryMethod> getDeliveryMethod(@PathVariable int id) {
        return new ResponseEntity<>(deliveryMethodService.fetchDeliveryMethodById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeliveryMethod> createDeliveryMethod(@RequestBody DeliveryMethod deliveryMethod) {
        return new ResponseEntity<>(deliveryMethodService.saveDeliveryMethod(deliveryMethod), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryMethod> updateDeliveryMethod(@PathVariable int id, @RequestBody DeliveryMethod deliveryMethod) {
        DeliveryMethod updatedDeliveryMethod = deliveryMethodService.updateDeliveryMethod(id, deliveryMethod);
        return new ResponseEntity<>(updatedDeliveryMethod, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryMethod(@PathVariable int id) {
        deliveryMethodService.deleteDeliveryMethodById(id);
        return ResponseEntity.noContent().build();
    }
}