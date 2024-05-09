package com.example.my_blockchain.consumer.dto;

public record ItemAttributeEvent(
    Long id,
    String name,
    Integer quantity,
    Integer unitPrice,
    Float weight,
    Float length,
    Float height,
    Float width,
    String itemCategory
) {
}