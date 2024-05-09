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
    private final WalletService walletService;
    private final TransactionService transactionService;

    @GetMapping("/test")
    public BlockchainResponse getMethodName() {
        String address = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgRV7neiu/muOhtEWsMQOjtkvwKvBxNQ2ZVYerXtNzTiHQzCAbWCXnyj2xsgD7D+fKud5Eg7ISwuCQDWlwn+vCw==";
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

        return blockchainService.mine(transactions);
    }

    @GetMapping("/get_last_block")
    public BlockchainResponse getLast() {
        return blockchainService.getLastBlock();
    }

    @GetMapping("/transact")
    public BlockchainResponse Transaction() {
        String senderAddress = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgRV7neiu/muOhtEWsMQOjtkvwKvBxNQ2ZVYerXtNzTiHQzCAbWCXnyj2xsgD7D+fKud5Eg7ISwuCQDWlwn+vCw==";
        String receiverAddress = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEqqeoAUaE0ViLB0D4GUanYCUqCeu9bR4TDWt6rtNyrfpXW+K9+9m3WP1t9E3lL8hCTcFlin9GjyzQWhr6GFuHCQ==";

        Order order = Order.builder()
            .order_id(1L)
            .created_at(LocalDateTime.now())
            .created_by("test")
            .totalWeight(2.0f)
            .totalPrice(2.0f)
            .note("test")
            .status(OrderStatus.CREATED)
            .build();
        
        List<Item_attribute> items = new ArrayList<Item_attribute>();
        items.add(Item_attribute.builder()
            .item_id(1L)
            .name("test")
            .quantity(1)
            .unitPrice(1)
            .weight(1.0f)
            .length(1.0f)
            .height(1.0f)
            .width(1.0f)
            .itemCategory("test")
            .build());
        
        items.add(Item_attribute.builder()
            .item_id(2L)
            .name("test item 2")
            .quantity(1)
            .unitPrice(1)
            .weight(1.0f)
            .length(1.0f)
            .height(1.0f)
            .width(1.0f)
            .itemCategory("test")
            .build());
            
        order.setItems(items);

        Input input = Input.builder()
            .address(senderAddress)
            .build();

        Output output = Output.builder()
            .address(receiverAddress)
            .orders(order)
            .build();
        
        Transaction transaction = Transaction.builder()
            .input(input)
            .outputs(Collections.singletonList(output))
            .build();
        
        return blockchainService.mine(Collections.singletonList(transactionService.signTransaction(transaction)));
    }
    
    @GetMapping("mining")
    public void mine() {
        blockchainService.startMine();
    }
    
}
