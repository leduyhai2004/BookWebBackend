package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.DeliveryMethod;
import com.duyhai.bookweb_backend.repository.DeliveryMethodRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryMethodService {
    private final DeliveryMethodRepository deliveryMethodRepository;

    public DeliveryMethodService(DeliveryMethodRepository deliveryMethodRepository) {
        this.deliveryMethodRepository = deliveryMethodRepository;
    }

    public List<DeliveryMethod> fetchAllDeliveryMethods() {
        return deliveryMethodRepository.findAll();
    }

    public DeliveryMethod fetchDeliveryMethodById(int id) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodRepository.findById(id);
        return deliveryMethod.orElseThrow(() -> new RuntimeException("Delivery method not found"));
    }

    public DeliveryMethod saveDeliveryMethod(DeliveryMethod deliveryMethod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        return deliveryMethodRepository.save(deliveryMethod);
    }

    public DeliveryMethod updateDeliveryMethod(int id, DeliveryMethod deliveryMethod) {
        DeliveryMethod existingDeliveryMethod = fetchDeliveryMethodById(id);
        if (existingDeliveryMethod != null) {
            existingDeliveryMethod.setDescription(deliveryMethod.getDescription());
            existingDeliveryMethod.setNameOfDeliveryMethod(deliveryMethod.getNameOfDeliveryMethod());
            existingDeliveryMethod.setPrice(deliveryMethod.getPrice());
            return deliveryMethodRepository.save(existingDeliveryMethod);
        }
        return null;
    }

    public void deleteDeliveryMethodById(int id) {
        deliveryMethodRepository.deleteById(id);
    }
}