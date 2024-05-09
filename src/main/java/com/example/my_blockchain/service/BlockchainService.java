package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.response.BlockchainResponse;

public interface BlockchainService {
    BlockchainResponse getBlock(BlockchainKey uuid);
    BlockchainResponse getLastBlock();
    List<BlockchainResponse> getAll();
    BlockchainResponse createBlock(Blockchain block);
    BlockchainResponse mine(List<Transaction> transactions);
    BlockchainResponse startMine();
}
