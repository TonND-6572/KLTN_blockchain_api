package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api-blockchain/blockchain")
@RequiredArgsConstructor
@Slf4j
public class BlockchainController {
    private final BlockchainService blockchainService;
    private final WalletService walletService;

    @GetMapping("/test")
    public BlockchainResponse getMethodName() {
        String address = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEmUKgIHbH7lV66QcQ+ZrZ282w3s+DxckC6IU5egnpv18/s7WYnCi3fFweBssGO8+yjYe4CHNzPvjPTXD7ULKerg==";
        Wallet wallet = walletService.getWallet(address);
        String msg = "Hello World";
        String msg2 = "Bye bye World";
        Transaction transaction = Transaction.builder()
            .input(
                new Input(address, BlockchainUtil.applySignature(wallet.getPrivate_key(), msg)))
            .outputs(null)
            .build();

        Transaction transaction2 = Transaction.builder()
            .input(
                new Input(address, BlockchainUtil.applySignature(wallet.getPrivate_key(), msg2)))
            .outputs(null)
            .build();
        
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        transactions.add(transaction2);
        log.info("{}", wallet.getAddress());

        return blockchainService.startMine(transactions);
    }

    @GetMapping("/get_last_block")
    public BlockchainResponse getLast() {
        return blockchainService.getLastBlock();
    }
}
