package com.example.my_blockchain.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.shaded.guava.common.base.Optional;
import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.CompositeKey.BlockchainKey;
import com.example.my_blockchain.model.mapper.BlockchainMapper;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.service.BlockchainService;

@Service
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
}
