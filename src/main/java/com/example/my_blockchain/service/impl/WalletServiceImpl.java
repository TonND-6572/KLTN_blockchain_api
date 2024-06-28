package com.example.my_blockchain.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.model.request.WalletGetRequest;
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
    public WalletResponse createWallet(Wallet wallet) {
        walletRepository.save(wallet);

        return walletMapper.toResponse(wallet);
    }

    @Override
    public Wallet getWallet(WalletGetRequest req) {
        Wallet wallet = walletRepository.findByAddress(req.address());
        if (wallet != null) {
            wallet.genKey();
            return wallet;
        }
        
        return null;
    }

    @Override
    @Transactional
    public Wallet addTransaction(String address, Transaction transaction) {
        try {
            if (address == "END_USER") return null;

            Wallet wallet = walletRepository.findByAddress(address);
            if (wallet == null)
                throw new RuntimeException(String.format("Wallet with address %s not found", address));
            
            List<Transaction> transactions = wallet.getTransactions();
            if (transactions == null) {
                transactions = Collections.singletonList(transaction);
            }
            else {
                transactions.add(transaction);
            }
            wallet.setTransactions(transactions);
            return walletRepository.save(wallet);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
