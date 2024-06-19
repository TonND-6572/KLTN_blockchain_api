package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.service.TransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api-blockchain/transaction-pool")
@RequiredArgsConstructor
public class TransactionPoolController {
    private final TransactionService transactionService;
    
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }
}
