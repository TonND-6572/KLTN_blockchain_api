package com.example.my_blockchain.model.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    String senderAddress,
    LocalDateTime createdTime,
    String signature,
    List<OutputResponse> outputs
) {
    
}
