package com.example.my_blockchain.model.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.my_blockchain.model.entity.UDT.Transaction;


public record BlockchainResponse(
    UUID uuid,
    Date created_time,
    String hash,
    String nonce,
    String previous_hash,
    List<Transaction> transactions
) {
    
}
