package com.example.my_blockchain.model.Response;

import java.util.List;

import com.example.my_blockchain.model.Entity.Transaction;

import lombok.*;

@Data
@Getter @Setter
@Builder
public class WalletResponse {
    public String address;
    public String secret;
    public List<Transaction> transactions;
}
