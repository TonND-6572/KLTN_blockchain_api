package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.service.BlockchainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



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
    
    
}
