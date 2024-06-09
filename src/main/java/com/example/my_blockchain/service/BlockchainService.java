package com.example.my_blockchain.service;

import java.util.List;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.response.BlockchainResponse;

public interface BlockchainService {
    BlockchainResponse getBlock(BlockchainKey uuid);
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
    void restoreBlockchain(List<Blockchain> inValidBlocks);
}
