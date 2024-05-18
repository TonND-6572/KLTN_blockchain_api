package com.example.my_blockchain.consumer.dto;

public record ItemAttributeEvent(
    Long id,
    String name,
    Integer quantity,
    Integer unitPrice,
    Float weight,
    String itemCategory
) {
}
