package com.example.my_blockchain.model.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.model.entity.UDT.Transaction;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(value="transaction_pool")
public class TransactionPool {
    @PrimaryKeyColumn(name = "transaction_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Id
    public Long transactionId;

    @Frozen
    public Transaction transaction;
}
