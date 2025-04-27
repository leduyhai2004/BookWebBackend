package com.duyhai.bookweb_backend.controller;

import com.duyhai.bookweb_backend.dto.request.OrderDTO;
import com.duyhai.bookweb_backend.entity.Order;
import com.duyhai.bookweb_backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = this.orderService.fetchAllOrders();
        if (orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderList);
    }
    //user
    @GetMapping("/user/all")
    public ResponseEntity<List<OrderDTO>> getAllOrdersOfAUser() {
        List<OrderDTO> orderList = this.orderService.fetchAllOrdersOfAUser();
        if (orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getDetailOrder(@PathVariable int id) {
        return new ResponseEntity<>(this.orderService.fetchOrderById(id), HttpStatus.OK);
    }
    //user
    @GetMapping("/user/{orderId}")
    public ResponseEntity<OrderDTO> getDetailOrderOfAUser(@PathVariable int orderId) {
        return new ResponseEntity<>(this.orderService.fetchOrderOfAUser(orderId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(this.orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int id, @RequestBody String deliveryStatus,
                                                @RequestBody String paymentStatus) {
        OrderDTO updateOrder = this.orderService.updateOrder(id,deliveryStatus,paymentStatus);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        this.orderService.deleteOrderById(id);
        return new ResponseEntity<>(this.orderService.fetchOrderById(id), HttpStatus.OK);
    }

}
