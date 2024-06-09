package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.response.OrderTrackingResponse;

public interface OrderTrackingService {
    List<OrderTrackingResponse> getAll();
    List<OrderTrackingResponse> getOrderTracking(Long orderId);
}
