package com.example.my_blockchain.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.OrderTracking;
import com.example.my_blockchain.model.entity.UDT.Output;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.model.mapper.BlockchainMapper;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.repo.OrderTrackingRepository;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.TransactionService;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;
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
    private final WalletService walletService;

    private final BlockchainRepository blockchainRepository;
    private final OrderTrackingRepository orderTrackingRepository;

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

            for (Transaction transaction : block.getTransactions()) {
                String sender = transaction.getInput().getAddress();
                for (Output output : transaction.getOutputs()) {
                    OrderTracking orderTracking = OrderTracking.builder()
                        .orderId(output.getOrders().getOrder_id())
                        .createdTime(output.getOrders().getCreated_at())
                        .status(output.getTransaction_status())
                        .sender(sender)
                        .receiver(output.getAddress())
                        .receiverName(output.getReceiverName())
                        .build();
                    
                    orderTrackingRepository.save(orderTracking);
                    walletService.addTransaction(output.getAddress(), transaction);
                }

                walletService.addTransaction(sender, transaction);
            }
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

    @Override
    public Boolean checkBlockchain() {
        List<Blockchain> blockchain = blockchainRepository.findAll();
        String previousHash = "";
        for (Blockchain block : blockchain) {
            if (!checkBlock(previousHash, block)){
                log.info(block.toString());
                return false;
            }
            previousHash = block.getHash();
        }
        return true;
    }

    private Boolean checkBlock(String previousHash, Blockchain block){
        // check prev_hash
        if (!previousHash.equals(block.getPrevious_hash())){
            return false;
        }

        // checking hash with difficulty
        if (!block.getHash().substring(0, block.getDifficulty())
            .equals(new String(new char[block.getDifficulty()]).replace('\0', '0'))){
            return false;
        }   

        // checking merkle root if block not a genesis block
        if (block.getNonce() > 0 && !BlockchainUtil.getMerkleRoot(block.getTransactions()).equals(block.getMerkle_root())){
            return false;
        }

        // checking hash
        if (!Blockchain.calculateHash(block).equals(block.getHash())){
            return false;
        }

        return true;
    }
}
