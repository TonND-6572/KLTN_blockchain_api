package com.example.my_blockchain.controller;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.request.BlockGetRequest;
import com.example.my_blockchain.service.BlockchainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-blockchain/blockchain")
@RequiredArgsConstructor
@Slf4j
public class BlockchainController {
    private final BlockchainService blockchainService;

    @GetMapping("/to-json")
    public void toJson() {
        blockchainService.toJson();
    }
    
    @GetMapping("/check")
    public ResponseEntity<?> check() {
        List<Blockchain> inValidBlocks = blockchainService.checkBlockchain();
        if (inValidBlocks.size() == 0) {
            return ResponseEntity.ok("Blockchain is valid");
        }
        return ResponseEntity.ok(inValidBlocks);
    }
    
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(blockchainService.getAll());
    }
    
    @GetMapping("/restore")
    public ResponseEntity<?> restoreBlockchain() {
        List<Blockchain> inValidBlocks = blockchainService.checkBlockchain();
        if (inValidBlocks.size() == 0) {
            return ResponseEntity.ok("Blockchain is valid!!! Nothing changed");
        }
        blockchainService.RestoreBlockchain(inValidBlocks);
        return ResponseEntity.ok("restore success");
    }
    
    @GetMapping("/detail")
    public ResponseEntity<?> getBlock(BlockGetRequest req) {
        
        return ResponseEntity.ok(blockchainService.getBlock(req));
    }
}
