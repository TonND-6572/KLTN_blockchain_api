package com.example.my_blockchain.model.Response;

import java.util.List;

import com.example.my_blockchain.model.Entity.Enum.WalletType;
import com.example.my_blockchain.model.Entity.UDT.Transaction;

import lombok.*;

@Data
@Getter @Setter
@Builder
public class WalletResponse {
    public String address;
    public String secret;
    public WalletType wallet_type;
    public List<Transaction> transactions;
}
