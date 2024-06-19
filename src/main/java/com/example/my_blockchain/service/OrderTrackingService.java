package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.entity.OrderTracking;

public interface OrderTrackingService {
    List<OrderTracking> orderTracking(Long orderId);
}
