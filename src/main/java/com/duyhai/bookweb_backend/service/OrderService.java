package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Order;
import com.duyhai.bookweb_backend.entity.OrderDetail;
import com.duyhai.bookweb_backend.entity.User;
import com.duyhai.bookweb_backend.repository.OrderDetailRepository;
import com.duyhai.bookweb_backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> fetchAllOrders() {
        return orderRepository.findAll();
    }
    public Order fetchOrderById(int id) {
        Optional<Order> detailOrder =  orderRepository.findById(id);
        return detailOrder.orElseThrow(() -> new RuntimeException("Order not found"));
    }
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    public void deleteOrderById(int id) {
       Optional<Order> orderOptional =  orderRepository.findById(id);
       if(orderOptional.isPresent()) {
           Order order = orderOptional.get();
           List<OrderDetail> orderDetails = order.getOrderDetailList();
           for(OrderDetail orderDetail : orderDetails) {
               this.orderDetailRepository.deleteById(orderDetail.getId());
           }
       }
    }
    public Order updateOrder(int id, Order order) {
        Order currentOrder = this.fetchOrderById(id);
        if(currentOrder != null) {
            currentOrder.setDeliveryStatus(order.getDeliveryStatus());
        }
        return orderRepository.save(order);
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }


}
