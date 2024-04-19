package com.example.my_blockchain.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.consumer.dto.WalletDTO;
import com.example.my_blockchain.model.Entity.Wallet;
import com.example.my_blockchain.model.Response.WalletResponse;
import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.repo.WalletRepository;
import com.example.my_blockchain.service.WalletService;

import lombok.RequiredArgsConstructor;

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
    public WalletResponse createWallet(Wallet wallet) {
        walletRepository.save(wallet);

        return walletMapper.toResponse(wallet);
    }
}
