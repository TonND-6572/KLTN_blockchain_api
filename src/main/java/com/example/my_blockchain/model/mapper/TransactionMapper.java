package com.example.my_blockchain.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.my_blockchain.consumer.dto.ItemAttributeEvent;
import com.example.my_blockchain.consumer.dto.OrderEvent;
import com.example.my_blockchain.model.entity.TransactionPool;
import com.example.my_blockchain.model.entity.UDT.Item_attribute;
import com.example.my_blockchain.model.entity.UDT.Order;
import com.example.my_blockchain.model.entity.UDT.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", source = "transactionId")
    @Mapping(target = "input", source = "transaction.input")
    @Mapping(target = "outputs", source = "transaction.outputs")
    @Mapping(target = "created_time", source = "transaction.created_time")
    Transaction toTransaction(TransactionPool transactionPool);

    List<Transaction> toTransactions(List<TransactionPool> transactionPools);

    @Mapping(target="item_id", source="id")
    Item_attribute toItem_attribute(ItemAttributeEvent event);
    
    @Mapping(target="created_at", ignore = true)
    Order toOrder(OrderEvent event);
}
