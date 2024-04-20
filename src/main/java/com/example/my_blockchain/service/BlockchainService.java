package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.CompositeKey.BlockchainKey;
import com.example.my_blockchain.model.response.BlockchainResponse;

public interface BlockchainService {
    BlockchainResponse getBlock(BlockchainKey uuid);
    List<BlockchainResponse> getAll();
    BlockchainResponse createBlock(Blockchain block);
}
