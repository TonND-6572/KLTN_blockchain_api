package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.TransactionPool;
import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.repo.TransactionPoolRepository;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api-blockchain/transaction-pool")
@RequiredArgsConstructor
public class TransactionPoolController {
    private final TransactionPoolRepository transactionPoolRepository;
    private final WalletService walletService;

    @GetMapping("test")
    public void test() {
        String address = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEqqeoAUaE0ViLB0D4GUanYCUqCeu9bR4TDWt6rtNyrfpXW+K9+9m3WP1t9E3lL8hCTcFlin9GjyzQWhr6GFuHCQ==";
        Wallet wallet = walletService.getWallet(address);
        wallet.genKey();

        String msg = "Hello World!";

        Transaction transaction = Transaction.builder()
            .input(Input.builder()
                .address(address)
                .signature(BlockchainUtil.applySignature(wallet.getPrivate_key(), msg))
                .build())
            .build();
        
        TransactionPool transactionPool = TransactionPool.builder()
            .transactionId(transaction.getId())
            .transaction(transaction)
            .build();

        transactionPoolRepository.save(transactionPool);

   }
    

    @GetMapping("/test2")
    public Boolean test2() throws NotFoundException {
        TransactionPool temp = transactionPoolRepository.findById(1L)
            .orElseThrow(() -> new NotFoundException());
        Wallet wallet = walletService.getWallet(temp.getTransaction().getInput().getAddress());
        wallet.genKey();
        // log.info("get: {}", temp.getTransaction().getInput().getSignature());
        return BlockchainUtil.verifySignature(wallet.getPublic_key(), temp.getTransaction().getOutputs().toString(), temp.getTransaction().getInput().getSignature());
    }
}
