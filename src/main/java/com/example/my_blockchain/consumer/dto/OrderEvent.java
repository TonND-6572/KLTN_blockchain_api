package com.example.my_blockchain.consumer.dto;

import java.util.List;

import com.example.my_blockchain.model.entity.Enum.OrderStatus;

public record OrderEvent(
    Long order_id,
    String createdAt,
    String createdBy,
    Float totalWeight,
    Float totalPrice,
    Float subTotal,
    Float feePaid,
    String note,
    OrderStatus status,
    List<ItemAttributeEvent> items
) {
} 
