package com.example.my_blockchain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.entity.Enum.OrderStatus;
import com.example.my_blockchain.model.entity.UDT.Input;
import com.example.my_blockchain.model.entity.UDT.Item_attribute;
import com.example.my_blockchain.model.entity.UDT.Order;
import com.example.my_blockchain.model.entity.UDT.Output;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.service.BlockchainService;
import com.example.my_blockchain.service.TransactionService;
import com.example.my_blockchain.service.WalletService;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api-blockchain/blockchain")
@RequiredArgsConstructor
@Slf4j
public class BlockchainController {
    private final BlockchainService blockchainService;

    @GetMapping("/to-json")
    public void toJson() {
        blockchainService.toJson();
    }
    
    
}
