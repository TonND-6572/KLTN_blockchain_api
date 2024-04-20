package com.example.my_blockchain.model.response;

import java.util.List;

import com.example.my_blockchain.model.entity.Enum.WalletType;
import com.example.my_blockchain.model.entity.UDT.Transaction;
 
public record WalletResponse (
    String address,
    String secret,
    WalletType wallet_type,
    List<Transaction> transactions
){
    
}
