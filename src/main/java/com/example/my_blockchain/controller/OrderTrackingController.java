package com.example.my_blockchain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.service.OrderTrackingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-blockchain/order-tracking")
@RequiredArgsConstructor
public class OrderTrackingController {
    private final OrderTrackingService orderTrackingService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderTrackingService.getAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderTracking(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderTrackingService.getOrderTracking(orderId));
    }
}
