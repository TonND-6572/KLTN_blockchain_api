package com.example.my_blockchain.service;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.util.BlockchainUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationStartService implements ApplicationRunner {
    private final BlockchainRepository blockchainRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        genesisBlock();
    }

    public void genesisBlock() {
        List<Blockchain> blocks = blockchainRepository.findAll();
        if (blocks.size() == 0) {
            Blockchain block = BlockchainUtil.genesis();
            log.info(block.toString());
            blockchainRepository.save(block);
            log.info("Genesis block created");
            return;
        }
        log.info("There is already a genesis block");
    }
}
