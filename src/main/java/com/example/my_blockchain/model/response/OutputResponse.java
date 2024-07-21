package com.example.my_blockchain.model.response;

import com.example.my_blockchain.model.entity.Enum.TransactionStatus;

public record OutputResponse(
    String receiverAddress,
	String receiverName,
	Integer transactionId,
	Integer orderId,
	TransactionStatus transactionStatus
) {
    
}
