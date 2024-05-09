package com.example.my_blockchain.consumer.dto;

import java.util.List;

public record OrderEvent(
    Long order_id,
    String created_at,
    String created_by,
    Float totalWeight,
    Float totalPrice,
    String note,
    String status,
    List<ItemAttributeEvent> items
) {
} 
