package com.example.my_blockchain.model.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import com.example.my_blockchain.model.Entity.UDT.Transaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.*;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class Blockchain {
    @PrimaryKey
    public String UUID;
    public LocalDateTime create_time;
    public String hash;
    public Integer nonce;
    public String previous_hash;
    
    public List<Transaction> transactions;
}
