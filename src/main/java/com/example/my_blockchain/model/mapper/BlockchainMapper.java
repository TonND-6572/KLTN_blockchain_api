package com.example.my_blockchain.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.UDT.Output;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.request.BlockGetRequest;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.model.response.OutputResponse;
import com.example.my_blockchain.model.response.TransactionResponse;

@Mapper(componentModel = "spring")
public interface BlockchainMapper {
    @Mapping(source = "bk.uuid", target = "uuid")
    @Mapping(target = "transactions")
    @Mapping(target = "createdTime", ignore = true)
    BlockchainResponse toResponse(Blockchain blockchain);

    @Mapping(source = "input.address", target = "senderAddress")
    @Mapping(source = "input.signature", target = "signature")
    // @Mapping(target = "createdTime", ignore = true)
    TransactionResponse toTransactionResponse(Transaction transaction);
    
    @Mapping(source = "address", target = "receiverAddress")
    @Mapping(source = "orders.order_id", target = "orderId")
    // @Mapping(ignore = true, target = "transactionStatus")
    OutputResponse toOutputResponse(Output output);
    
    BlockchainKey toKey(BlockGetRequest request);
}
