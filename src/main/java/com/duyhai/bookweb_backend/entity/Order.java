package com.duyhai.bookweb_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private int order_id;

    @Column(name = "order_date")
    private Date order_date;

    @Column(name = "address_of_buyer")
    private String addressOfBuyer;

    @Column(name = "address_of_seller")
    private String addressOfSeller;

    @Column(name = "price")
    private double price;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<OrderDetail> orderDetailList;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "payment_id")
    private Payment payment;


    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethod deliveryMethod;
}
