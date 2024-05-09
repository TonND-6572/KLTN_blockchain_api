package com.example.my_blockchain.service.impl;


import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.model.response.WalletResponse;
import com.example.my_blockchain.repo.WalletRepository;
import com.example.my_blockchain.service.WalletService;


@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletServiceImpl(WalletRepository walletRepository, WalletMapper walletMapper){
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }
    
    @Override
    @Transactional
    public ResponseEntity<WalletResponse> createWallet(Wallet wallet) {
        walletRepository.save(wallet);

        return ResponseEntity.ok(walletMapper.toResponse(wallet));
    }

    @Override
    public Wallet getWallet(String address) {
        Wallet wallet = walletRepository.findByAddress(address);
        if (wallet != null) {
            wallet.genKey();
            return wallet;
        }
        
        return null;
    }
}
