package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.model.request.WalletGetRequest;
import com.example.my_blockchain.service.WalletService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api-blockchain/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @GetMapping("")
    public ResponseEntity<?> getWallet(@RequestBody WalletGetRequest address) {
        return ResponseEntity.ok(walletMapper.toResponse(walletService.getWallet(address)));
    }
    
}
