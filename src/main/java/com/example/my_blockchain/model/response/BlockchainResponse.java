package com.example.my_blockchain.model.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.my_blockchain.model.entity.UDT.Transaction;


public record BlockchainResponse(
    UUID uuid,
    LocalDateTime created_time,
    String hash,
    Long nonce,
    Integer difficulty,
    String previous_hash,
    List<Transaction> transactions
) {
    
}
