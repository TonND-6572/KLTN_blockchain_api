package com.example.my_blockchain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_blockchain.model.entity.OrderTracking;
import com.example.my_blockchain.repo.OrderTrackingRepository;
import com.example.my_blockchain.service.OrderTrackingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderTrackingServiceImpl implements OrderTrackingService {
    private final OrderTrackingRepository orderTrackingRepository;

    @Override
    public List<OrderTracking> orderTracking(Long id) {
        return orderTrackingRepository.findByOrderId(id);
    }
    
}
