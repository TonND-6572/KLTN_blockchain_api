package com.example.my_blockchain.service;

import org.springframework.http.ResponseEntity;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.response.WalletResponse;

public interface WalletService {
    ResponseEntity<WalletResponse> createWallet(Wallet wallet);
    Wallet getWallet(String address);
}
