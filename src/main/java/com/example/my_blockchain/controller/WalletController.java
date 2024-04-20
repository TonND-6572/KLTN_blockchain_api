package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.repo.WalletRepository;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api-blockchain/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    
}
