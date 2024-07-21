package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.request.BlockGetRequest;
import com.example.my_blockchain.model.response.BlockchainResponse;

public interface BlockchainService {
    BlockchainResponse getBlock(BlockGetRequest request);
    BlockchainResponse getLastBlock();
    List<BlockchainResponse> getAll();
    BlockchainResponse createBlock(Blockchain block);
    BlockchainResponse startMine();
    void toJson();
    /*
     * return:
     *   List of blockchain that are error
     *   Or null if none of them
     */
    List<Blockchain> checkBlockchain();
    /*
     * remove invalid blocks from blockchain
     */
    void RestoreBlockchain(List<Blockchain> inValidBlocks);
}
