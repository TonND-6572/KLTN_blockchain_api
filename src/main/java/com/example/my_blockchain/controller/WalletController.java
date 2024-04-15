package com.example.my_blockchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.Entity.Wallet;
import com.example.my_blockchain.model.Request.WalletGetRequest;
import com.example.my_blockchain.model.Response.WalletResponse;
import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.repo.WalletRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api/blockchain/wallet")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletMapper walletMapper;

    Wallet wallet = new Wallet();
    
    @GetMapping("/create")
    public ResponseEntity<WalletResponse> create() {
        System.out.println(wallet.toString());
        walletRepository.save(wallet);

        return new ResponseEntity<>(walletMapper.toResponse(wallet), null, 200);
    }

    @PostMapping("/get")
    public ResponseEntity<WalletResponse> getWallet(@RequestBody WalletGetRequest req) {
        System.out.println("Im here");
        Wallet entity = walletRepository.findByAddress(req.getAddress());

        Wallet test_wallet = new Wallet(entity.getAddress(), entity.getSecret(), entity.getSalt_iv(), null);
        System.out.println(wallet.getPrivate_key().equals(test_wallet.getPrivate_key()));
        System.out.println(wallet.getPublic_key().equals(test_wallet.getPublic_key()));
        
        return new ResponseEntity<>(walletMapper.toResponse(test_wallet), null, 200);
    }

    @GetMapping("/")
    public ResponseEntity<List<WalletResponse>> getAll() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletResponse> wallets_reponse = walletMapper.toResponse(wallets);
        return new ResponseEntity<List<WalletResponse>>(wallets_reponse, null, 200);
    }
}
