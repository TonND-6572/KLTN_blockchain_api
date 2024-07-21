package com.example.my_blockchain.model.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockGetRequest (
    UUID uuid,
    LocalDateTime createdTime,
    Integer year
) {
    
}
