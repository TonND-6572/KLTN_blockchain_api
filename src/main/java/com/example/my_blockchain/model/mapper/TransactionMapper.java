package com.example.my_blockchain.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.my_blockchain.consumer.dto.ItemAttributeEvent;
import com.example.my_blockchain.consumer.dto.OrderEvent;
import com.example.my_blockchain.model.entity.TransactionPool;
import com.example.my_blockchain.model.entity.UDT.ItemAttribute;
import com.example.my_blockchain.model.entity.UDT.Order;
import com.example.my_blockchain.model.entity.UDT.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", source = "transaction.id")
    @Mapping(target = "input", source = "transaction.input")
    @Mapping(target = "outputs", source = "transaction.outputs")
    @Mapping(target = "createdTime", source = "transaction.createdTime")
    Transaction toTransaction(TransactionPool transactionPool);

    @Mapping(target = "senderAddress", source = "transaction.input.address")
    @Mapping(target = "transaction", source = "transaction")
    TransactionPool toTransactionPool(Transaction transaction);

    List<Transaction> toTransactions(List<TransactionPool> transactionPools);
    List<TransactionPool> toTransactionPools(List<Transaction> transactions);

    @Mapping(target="itemId", source="id")
    ItemAttribute toItemAttribute(ItemAttributeEvent event);
    
    @Mapping(target="created_at", ignore = true)
    Order toOrder(OrderEvent event);
}
