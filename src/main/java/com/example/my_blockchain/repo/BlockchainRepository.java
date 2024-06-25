package com.example.my_blockchain.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;

@Repository
public interface BlockchainRepository extends CassandraRepository<Blockchain, BlockchainKey>{
    @Query("select * from blockchain_by_year WHERE year = 2024 LIMIT 1")
    Blockchain findLastBlock();

    @Query("select * from blockchain_by_year WHERE year = 2024 AND id = ? LIMIT 1")
    Blockchain findByUUID(UUID uuid);
}
