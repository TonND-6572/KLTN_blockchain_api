package com.example.my_blockchain.repo;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.example.my_blockchain.model.entity.TransactionPool;

public interface TransactionPoolRepository extends CassandraRepository<TransactionPool, Long> {
    
}
