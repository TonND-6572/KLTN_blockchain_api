package com.example.my_blockchain.service;

import com.example.my_blockchain.consumer.dto.WalletDTO;
import com.example.my_blockchain.model.Entity.Wallet;
import com.example.my_blockchain.model.Response.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(Wallet wallet);
}
