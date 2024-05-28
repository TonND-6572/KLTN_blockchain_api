package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.consumer.dto.TransactionEventDTO;
import com.example.my_blockchain.model.entity.TransactionPool;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Output;
import com.example.my_blockchain.model.entity.UDT.Transaction;

public interface TransactionService {
    TransactionPool createTransaction(Input input, List<Output> outputs);
    TransactionPool createTransaction(TransactionEventDTO event);
    TransactionPool createTransaction(Input input, Output outputs);
    void createTransaction(List<Transaction> transactions);
    Transaction signTransaction(Transaction transaction);
    Boolean verifyTransaction(Transaction transaction);
    void clearTransactionPool();
    void clearTransactionPool(List<Transaction> transactions);
    List<Transaction> getTransactions();
}