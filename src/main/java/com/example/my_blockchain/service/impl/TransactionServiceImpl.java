package com.example.my_blockchain.service.impl;

import java.security.PublicKey;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.consumer.dto.OrderEvent;
import com.example.my_blockchain.consumer.dto.TransactionEventDTO;
import com.example.my_blockchain.model.entity.TransactionPool;
import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Order;
import com.example.my_blockchain.model.entity.UDT.Output;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.mapper.TransactionMapper;
import com.example.my_blockchain.repo.TransactionPoolRepository;
import com.example.my_blockchain.repo.WalletRepository;
import com.example.my_blockchain.service.TransactionService;
// import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;
import com.example.my_blockchain.util.Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final WalletRepository walletRepository;
    private final TransactionPoolRepository transactionPoolRepository;
    private final TransactionMapper transactionMapper;
    
    @Override
    @Transactional
    public TransactionPool createTransaction(TransactionEventDTO event) {
        //Input
        if ((walletRepository.findByAddress(event.getSenderAddress()) == null)) {
            throw new RuntimeException("Wallet not found");
        }

        Input input = Input.builder()
            .address(event.getSenderAddress())
            .build();
        //Output

        Order order = transactionMapper.toOrder(event.getOrderEvent());
        order.setCreated_at(Util.parseTime(event.getCreateAt()));

        Output output = Output.builder()
            .address(event.getReceiverAddress())
            .receiverName(event.getReceiverName())
            .transaction_status(event.getStatus())
            .orders(order)
            .build();
        //Transaction

        return createTransaction(input, output, event.getTransaction_id());
    }

    @Override
    @Transactional
    public TransactionPool createTransaction(Input input, Output outputs, Long transaction_id) {
        Transaction transaction = Transaction.builder()
            .id(transaction_id)
            .input(input)
            .outputs(Collections.singletonList(outputs))
            .build();

        TransactionPool transactionPool = TransactionPool.builder()
            .transactionId(transaction.getId())
            .transaction(signTransaction(transaction))
            .build();
        
        return transactionPoolRepository.save(transactionPool);
    }

    @Override
    @Transactional
    public TransactionPool createTransaction(Input input, List<Output> outputs, Long transaction_id) {
        Transaction transaction = Transaction.builder()
            .id(transaction_id)
            .input(input)
            .outputs(outputs)
            .build();

        TransactionPool transactionPool = TransactionPool.builder()
            .transactionId(transaction.getId())
            .transaction(signTransaction(transaction))
            .build();
        
        return transactionPoolRepository.save(transactionPool);
    }

    public Transaction signTransaction(Transaction transaction) {
        String data = transaction.getOutputs().toString();
        Wallet wallet = walletRepository.findByAddress(transaction.getInput().getAddress());
        wallet.genKey();

        transaction.getInput()
            .setSignature(BlockchainUtil.applySignature(wallet.getPrivate_key(), data));
        return transaction;
    }
    
    @Override
    public Boolean verifyTransaction(Transaction transaction) {
        String data = transaction.getOutputs().toString();
        PublicKey senderPublicKey = BlockchainUtil.getPublicKey(transaction.getInput().getAddress());
        
        Boolean verified = BlockchainUtil.verifySignature(senderPublicKey, data, transaction.getInput().getSignature());
        return verified;
    }

    @Override
    @Transactional
    public void clearTransactionPool() {
        transactionPoolRepository.deleteAll();
    }

    @Override
    @Transactional
    public void clearTransactionPool(List<Transaction> transactions) {
        transactionPoolRepository.deleteAllById(transactions.stream().map(Transaction::getId).toList());
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionMapper.toTransactions(transactionPoolRepository.findAll());
    }
}
