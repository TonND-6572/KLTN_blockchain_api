package com.example.my_blockchain.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
import com.example.my_blockchain.model.request.BlockGetRequest;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.repo.OrderTrackingRepository;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.TransactionService;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;
import com.example.my_blockchain.util.Configuration;

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
    public BlockchainResponse getBlock(BlockGetRequest request) {
        Instant instant = request.createdTime().minusHours(7).toInstant(ZoneOffset.UTC);
        System.out.println(instant);
        Date date = Date.from(instant);
        Blockchain block = blockchainRepository.getBlockDetail(request.year(), date, request.uuid());
        
        if (block == null) {
            throw new RuntimeException("Block not found");
        }
        return blockchainMapper.toResponse(block);
    }

    public BlockchainResponse getBlock(BlockchainKey uuid) {
        return blockchainRepository.findById(uuid)
            .map(blockchainMapper::toResponse).orElse(null);
    }

    @Override
    public List<BlockchainResponse> getAll() {
      return blockchainRepository.findAll().stream()
        .map(block ->toResponse(block))
        .limit(10)
        .toList();
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
                        .status(output.getTransactionStatus())
                        .sender(sender)
                        .receiver(output.getAddress())
                        .receiverName(output.getReceiverName())
                        .build();
                    
                    orderTrackingRepository.save(orderTracking);
                    if (output.getAddress().equals(sender) || output.getAddress().equals("END_USER")) continue;
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

            if (validTransactions.size() == Configuration.MAX_TRANSACTION) break;
        }

        Blockchain last_block = blockchainRepository.findLastBlock();
        Blockchain block = Blockchain.mineBlock(last_block, validTransactions);
        
        return createBlock(block);
    }

    @Override
    public void RestoreBlockchain(List<Blockchain> inValidBlocks) {
        List<BlockchainKey> uuids = inValidBlocks.stream().map(Blockchain::getBk).toList();
        List<Transaction> transactions = inValidBlocks.stream().map(Blockchain::getTransactions)
            .flatMap(List::stream).toList();
        transactionService.createTransaction(transactions);
        blockchainRepository.deleteAllById(uuids);
    }

    @Override
    public List<Blockchain> checkBlockchain() {
        List<Blockchain> blockchain = blockchainRepository.findAll();
        List<Blockchain> inValidBlocks = new ArrayList<>();
        Collections.reverse(blockchain);
        Iterator<Blockchain> iterator = blockchain.iterator();
        String previousHash = "";

        while (iterator.hasNext()) {
            Blockchain block = iterator.next();
            if (!isValidBlock(previousHash, block)){
                log.info("{}", block.getBk().getUuid());
                while (iterator.hasNext()){
                    inValidBlocks.add(block);
                    block = iterator.next();
                }
                inValidBlocks.add(block);
                break;
            }
            previousHash = block.getHash();
        }
        return inValidBlocks;
    }

    private Boolean isValidBlock(String previousHash, Blockchain block){
        // checking hash
        if (!Blockchain.calculateHash(block).equals(block.getHash())){
            return false;
        }
        
        // check prev_hash
        if (!block.getPrevious_hash().equals(previousHash)){
            return false;
        }

        // checking genesis block
        if (previousHash.equals("") &&
                block.getMerkle_root().equals("")) return true; 

        // checking hash with difficulty
        if (!block.getHash().substring(0, block.getDifficulty())
            .equals(new String(new char[block.getDifficulty()]).replace('\0', '0'))){
            return false;
        }   

        // checking merkle root if block not a genesis block
        if (block.getNonce() > 0 && 
            !BlockchainUtil.getMerkleRoot(block.getTransactions()).equals(block.getMerkle_root())){
            return false;
        }


        return true;
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

    private BlockchainResponse toResponse(Blockchain block){
        BlockchainResponse blockchainResponse = blockchainMapper.toResponse(block);
        ZoneId zoneId = ZoneId.systemDefault();
        block.getBk().getCreatedTime().atZone(zoneId);
        Instant instant = block.getBk().getCreatedTime().minusHours(7).toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);
        
        return blockchainResponse.setCreatedTime(date);
    }
}
