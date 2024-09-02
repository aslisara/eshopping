package com.jetbrains.eshopping.controller;


import com.jetbrains.eshopping.dto.OrderDTO;
import com.jetbrains.eshopping.dto.OrderDetailDTO;
import com.jetbrains.eshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Yeni sipariş oluşturma
    @PostMapping("/{userId}")
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable("userId") Long userId,
            @RequestBody List<OrderDetailDTO> orderDetails) {
        OrderDTO createdOrder = orderService.createOrder(userId, orderDetails);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Kullanıcı ID'sine göre siparişleri getirme
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
