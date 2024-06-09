package com.example.my_blockchain.model.response;

import java.time.LocalDateTime;
import com.example.my_blockchain.model.entity.Enum.TransactionStatus;

public record OrderTrackingResponse(
    Long orderId,
    LocalDateTime createdTime,
    TransactionStatus status,
    String sender,
    String receiver,
    String receiverName
) {}
