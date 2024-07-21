package com.example.my_blockchain.model.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public record BlockchainResponse(
    UUID uuid,
    Date createdTime,
    String hash,
    Long nonce,
    Integer difficulty,
    String previous_hash,
    List<TransactionResponse> transactions
) {
    public BlockchainResponse setCreatedTime(Date createdTime) {
        return new BlockchainResponse(uuid, createdTime, hash, nonce, difficulty, previous_hash, transactions);
    }
}
