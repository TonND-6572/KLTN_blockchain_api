package com.example.my_blockchain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_blockchain.model.entity.OrderTracking;
import com.example.my_blockchain.model.mapper.OrderTrackingMapper;
import com.example.my_blockchain.model.response.OrderTrackingResponse;
import com.example.my_blockchain.repo.OrderTrackingRepository;
import com.example.my_blockchain.service.OrderTrackingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderTrackingServiceImpl implements OrderTrackingService {
    private final OrderTrackingMapper orderTrackingMapper;
    private final OrderTrackingRepository orderTrackingRepository;

    @Override
    public List<OrderTrackingResponse> getAll() {
        return orderTrackingRepository.findAll()
            .stream().map(orderTrackingMapper::toResponse).toList();
    }

    @Override
    public List<OrderTrackingResponse> getOrderTracking(Long orderId) {
        List<OrderTracking> orderTrackings = orderTrackingRepository.findByOrderId(orderId);

        if (orderTrackings != null) {
            return orderTrackings
                .stream().map(orderTrackingMapper::toResponse).toList();
        }
        return null;
    }    
}
