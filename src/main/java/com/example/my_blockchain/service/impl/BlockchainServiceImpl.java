package com.example.my_blockchain.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.mapper.BlockchainMapper;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.TransactionService;
import com.example.my_blockchain.util.LocalDateTimeDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService{
    private final TransactionService transactionService;
    private final BlockchainRepository blockchainRepository;
    private final BlockchainMapper blockchainMapper;

    @Override
    public BlockchainResponse getBlock(BlockchainKey uuid) {
        return blockchainRepository.findById(uuid).map(blockchainMapper::toResponse).orElse(null);
    }

    @Override
    public List<BlockchainResponse> getAll() {
      return blockchainRepository.findAll().stream().map(blockchainMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public BlockchainResponse createBlock(Blockchain block) {
        try {
            blockchainRepository.save(block);
            transactionService.clearTransactionPool(block.getTransactions());

        } catch (Exception e) {
            log.error("{}", e);
        }
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
    public BlockchainResponse startMine() {
        List<Transaction> transactions = transactionService.getTransactions();
        if (transactions.size() == 0){
            return null;
        }
        return mine(transactions);
    }

    @Override
    @Async
    public BlockchainResponse mine(List<Transaction> transactions) {
        List<Transaction> validTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            log.info("{}", transaction);

            if (transactionService.verifyTransaction(transaction)){
                validTransactions.add(transaction);
            }
        }

        Blockchain last_block = blockchainRepository.findLastBlock();
        Blockchain block = Blockchain.mineBlock(last_block, validTransactions);
        
        return createBlock(block);
    }

    @Override
    public void toJson() {
        List<Blockchain> blocks = blockchainRepository.findAll();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            }
        }).create();


        // Converts Java object to File
        try (Writer writer = new FileWriter("blockchain.json")) {
            gson.toJson(blocks, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
