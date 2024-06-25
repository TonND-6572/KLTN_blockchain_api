package com.example.my_blockchain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.service.OrderTrackingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-blockchain/order-tracking")
public class OrderTrackingController {
    private final OrderTrackingService orderTrackingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> orderTracking(@PathVariable("id") Long orderId) {
        return ResponseEntity.ok(orderTrackingService.orderTracking(orderId));
    }
}
