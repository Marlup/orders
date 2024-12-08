package com.marlo.orders.controller;

import com.marlo.orders.entity.Order;
import com.marlo.orders.kafka.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        try {
            orderProducer.sendOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order sent to Kafka.");
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send order.");
        }
    }
}
