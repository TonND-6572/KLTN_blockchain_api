package com.example.my_blockchain.consumer.dto;

import com.example.my_blockchain.model.entity.Enum.TransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionEventDTO {
    private String senderAddress;
    private String receiverAddress;
    private String receiverName;
    private Long transaction_id;
    private OrderEvent orderEvent;
    private TransactionStatus status;
    private String createAt;
}
