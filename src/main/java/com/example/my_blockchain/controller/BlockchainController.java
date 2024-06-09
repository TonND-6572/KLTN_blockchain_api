package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.service.BlockchainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api-blockchain/blockchain")
@RequiredArgsConstructor
@Slf4j
public class BlockchainController {
    private final BlockchainService blockchainService;

    @GetMapping("/to-json")
    public ResponseEntity<?> toJson() {
        blockchainService.toJson();
        return ResponseEntity.ok("Json have been created");
    }
    
    @GetMapping("/checking")
    public ResponseEntity<?> checkBlockchain() {
        List<Blockchain> inValidBlocks = blockchainService.checkBlockchain();
        if (inValidBlocks.size() > 0){
            return ResponseEntity.ok(inValidBlocks);
        }
        return ResponseEntity.ok("Blockchain is valid");
    }

    @GetMapping("/restore")
    public ResponseEntity<?> restoreBlockchain() {
        List<Blockchain> inValidBlocks = blockchainService.checkBlockchain();
        blockchainService.restoreBlockchain(inValidBlocks);
        return ResponseEntity.ok(inValidBlocks);
    }
    
    @GetMapping("")
    public ResponseEntity<?>  getAll() {
        return ResponseEntity.ok(blockchainService.getAll());
    }
    
}
