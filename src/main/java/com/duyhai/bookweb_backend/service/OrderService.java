package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.dto.request.OrderDTO;
import com.duyhai.bookweb_backend.dto.request.OrderDetailDTO;
import com.duyhai.bookweb_backend.entity.*;
import com.duyhai.bookweb_backend.repository.OrderDetailRepository;
import com.duyhai.bookweb_backend.repository.OrderRepository;
import com.duyhai.bookweb_backend.util.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;
    private final PaymentService paymentService;
    private final DeliveryMethodService deliveryMethodService;
    private final BookService bookService;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        UserService userService,
                        PaymentService paymentService,
                        DeliveryMethodService deliveryMethodService,
                        BookService bookService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
        this.paymentService = paymentService;
        this.deliveryMethodService = deliveryMethodService;
        this.bookService = bookService;
    }

    public List<Order> fetchAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDTO> fetchAllOrdersOfAUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        List<Order> orderList = this.orderRepository.findByUser(user);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(order -> {
            List<OrderDetailDTO> orderDetailDTOList = order.getOrderDetailList().stream()
                    .map(orderDetail -> OrderDetailDTO.builder()
                            .bookId(orderDetail.getBook().getId())
                            .quantity(orderDetail.getQuantity())
                            .price(orderDetail.getPrice())
                            .build()).toList();
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(order.getOrderId())
                    .addressOfBuyer(order.getAddressOfBuyer())
                    .price(order.getPrice())
                    .paymentId(order.getPayment().getId())
                    .deliveryMethodId(order.getDeliveryMethod().getId())
                    .items(orderDetailDTOList)
                    .deliveryStatus(order.getDeliveryStatus())
                    .paymentStatus(order.getPaymentStatus())
                    .build();
            orderDTOList.add(orderDTO);
        });
        return orderDTOList;
    }
    public Order fetchOrderById(int id) {
        Optional<Order> detailOrder =  orderRepository.findById(id);
        return detailOrder.orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public OrderDTO fetchOrderOfAUser(int orderId) {
        UserUtil userUtil = new UserUtil(userService);
        User currentUser = userUtil.getCurrentUser();
        if(this.orderRepository.existsByOrderIdAndUser(orderId,currentUser)){}
        Order order = fetchOrderById(orderId);
        List<OrderDetailDTO> orderDetailDTOList = order.getOrderDetailList().stream()
                .map(orderDetail -> OrderDetailDTO.builder()
                        .bookId(orderDetail.getBook().getId())
                        .quantity(orderDetail.getQuantity())
                        .price(orderDetail.getPrice())
                        .build()).toList();
        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getOrderId())
                .addressOfBuyer(order.getAddressOfBuyer())
                .price(order.getPrice())
                .paymentId(order.getPayment().getId())
                .deliveryMethodId(order.getDeliveryMethod().getId())
                .items(orderDetailDTOList)
                .build();
        return orderDTO;
    }

    public Order saveOrder(OrderDTO orderRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        Order newOrder = new Order();
        newOrder.setOrderDate(new Date());
        newOrder.setAddressOfBuyer(orderRequest.getAddressOfBuyer());
        newOrder.setPrice(orderRequest.getPrice());
        newOrder.setPaymentStatus("PENDING");
        newOrder.setDeliveryStatus("PENDING");
        newOrder.setUser(user);
        Payment payment = paymentService.getPaymentById(orderRequest.getPaymentId());
        newOrder.setPayment(payment);

        DeliveryMethod delivery = deliveryMethodService.fetchDeliveryMethodById(orderRequest.getDeliveryMethodId());
        newOrder.setDeliveryMethod(delivery);

        List<OrderDetail> detailList = new ArrayList<>();
        for (OrderDetailDTO item : orderRequest.getItems()) {
            Book book = bookService.handleGetBookById(item.getBookId());
            OrderDetail detail = new OrderDetail();
            detail.setBook(book);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            detail.setOrder(newOrder);
            detailList.add(detail);
        }
        newOrder.setOrderDetailList(detailList);
        return orderRepository.save(newOrder);
    }

    //admin or user, admin: delete, user : cancel
    public void deleteOrderById(int id) {
       Optional<Order> orderOptional =  orderRepository.findById(id);
       if(orderOptional.isPresent()) {
           Order order = orderOptional.get();
           List<OrderDetail> orderDetails = order.getOrderDetailList();
           for(OrderDetail orderDetail : orderDetails) {
               this.orderDetailRepository.deleteById(orderDetail.getId());
           }
       }
       this.orderRepository.deleteById(id);
    }
    public OrderDTO updateOrder(int id, String deliveryStatus, String paymentStatus) {
        Order currentOrder = this.fetchOrderById(id);
        if(currentOrder != null) {
            currentOrder.setDeliveryStatus(deliveryStatus);
            currentOrder.setPaymentStatus(paymentStatus);
        }
        orderRepository.save(currentOrder);
        OrderDTO orderDTO = OrderDTO.builder()
                .id(currentOrder.getOrderId())
                .addressOfBuyer(currentOrder.getAddressOfBuyer())
                .price(currentOrder.getPrice())
                .paymentId(currentOrder.getPayment().getId())
                .deliveryMethodId(currentOrder.getDeliveryMethod().getId())
                .deliveryStatus(currentOrder.getDeliveryStatus())
                .paymentStatus(currentOrder.getPaymentStatus())
                .build();
        return orderDTO;
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }


}
