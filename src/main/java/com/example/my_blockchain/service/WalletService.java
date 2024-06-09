package com.example.my_blockchain.service;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.response.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(Wallet wallet);
    Wallet getWallet(String address);
    WalletResponse get(String address);
    Wallet addTransaction(String address, Transaction transaction);
}
