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

    @GetMapping("/admin/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderList = this.orderService.fetchAllOrders();
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
    //admin
    @GetMapping("admin/{orderId}")
    public ResponseEntity<OrderDTO> getDetailOrder(@PathVariable int orderId) {
        return new ResponseEntity<>(this.orderService.fetchOrderDTOById(orderId), HttpStatus.OK);
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

    @PutMapping("admin/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int id,
                                                @RequestParam("deliveryStatus") String deliveryStatus,
                                                @RequestParam("paymentStatus")  String paymentStatus,
                                                @RequestParam("addressOfBuyer")  String addressOfBuyer
                                                ) {
        OrderDTO updateOrder = this.orderService.updateOrder(id,deliveryStatus,paymentStatus,addressOfBuyer);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int id,
                                                @RequestParam("addressOfBuyer") String addressOfBuyer
    ) {
        OrderDTO updateOrder = this.orderService.updateOrderOfAUser(id,addressOfBuyer);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        this.orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

}
