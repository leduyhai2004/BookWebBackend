package com.duyhai.bookweb_backend.dto.request;

import com.duyhai.bookweb_backend.entity.DeliveryMethod;
import com.duyhai.bookweb_backend.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private String addressOfBuyer;
    private double price;
    private int paymentId;
    private int deliveryMethodId;
    private List<OrderDetailDTO> items;
    private String paymentStatus;
    private String deliveryStatus;
}
