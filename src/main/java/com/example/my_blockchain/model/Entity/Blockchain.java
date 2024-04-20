package com.example.my_blockchain.model.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.model.entity.CompositeKey.BlockchainKey;
import com.example.my_blockchain.model.entity.UDT.Transaction;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Table(value="blockchain")
public class Blockchain {
    @PrimaryKey
    @Builder.Default
    public BlockchainKey bk = new BlockchainKey();

    public String hash;
    public Integer nonce;
    public String previous_hash;
    
    public Set<Transaction> transactions;
}
