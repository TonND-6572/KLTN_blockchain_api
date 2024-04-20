package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
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
    public void getMethodName() {
        String address = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEr6tQO54RBfrSGViHmtHvN8IeluGEZc96sgOonOcThIso9q2KgZito+F3wC/fnq5MKJwipBv7+lNftf6XS6ZLJA==";
        Wallet wallet = walletService.getWallet(address);
        String msg = "Hello World";

        // Transaction transaction = new Transaction();
        // transaction.setInput(
        //     new Input(address, null));
        // transaction.setOutputs(null);
        Transaction transaction = Transaction.builder()
            .input(
                new Input(address, BlockchainUtil.applySignature(wallet.getPrivate_key(), msg)))
            .outputs(null)
            .build();
        log.info("{}", wallet.getAddress());
        
        Blockchain block = Blockchain.builder()
            .hash("hash")
            .nonce(0)
            .previous_hash("previous_hash")
            .transactions(Collections.singleton(transaction)).build();
            // .transactions(null).build();

        log.info("{}", block.getBk());

        blockchainService.createBlock(block);
    }
}
