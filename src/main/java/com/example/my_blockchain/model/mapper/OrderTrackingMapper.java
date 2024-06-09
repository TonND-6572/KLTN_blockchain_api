package com.example.my_blockchain.model.mapper;

import org.mapstruct.Mapper;

import com.example.my_blockchain.model.entity.OrderTracking;
import com.example.my_blockchain.model.response.OrderTrackingResponse;

@Mapper(componentModel = "spring")
public interface OrderTrackingMapper {
    OrderTrackingResponse toResponse(OrderTracking orderTracking);
}
