package com.example.my_blockchain.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.shaded.guava.common.base.Optional;
import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.mapper.BlockchainMapper;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BlockchainServiceImpl implements BlockchainService {

    private final BlockchainRepository blockchainRepository;
    private final BlockchainMapper blockchainMapper;

    public BlockchainServiceImpl(BlockchainRepository blockchainRepository, BlockchainMapper blockchainMapper) {
        this.blockchainRepository = blockchainRepository;
        this.blockchainMapper = blockchainMapper;
    }

    @Override
    public BlockchainResponse getBlock(BlockchainKey uuid) {
        return blockchainRepository.findById(uuid).map(blockchainMapper::toResponse).orElse(null);
    }

    @Override
    public List<BlockchainResponse> getAll() {
      return blockchainRepository.findAll().stream().map(blockchainMapper::toResponse).toList();
    }

    @Override
    public BlockchainResponse createBlock(Blockchain block) {
        blockchainRepository.save(block);
        
        return blockchainMapper.toResponse(block);
    }

    @Override
    public BlockchainResponse getLastBlock() {
        Blockchain block = blockchainRepository.findLastBlock();
        // List<Blockchain> blocks = blockchainRepository.findAll();
        // blocks.stream().forEach(a -> log.info("{}", a));
        return blockchainMapper.toResponse(block);
    }

    @Override
    @Async
    public BlockchainResponse startMine(List<Transaction> transactions) {
        Blockchain last_block = blockchainRepository.findLastBlock();
        Blockchain block = Blockchain.mineBlock(last_block, transactions);

        
        return createBlock(block);
    }
}
