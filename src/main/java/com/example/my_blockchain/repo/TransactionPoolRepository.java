package com.example.my_blockchain.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.TransactionPool;

@Repository
public interface TransactionPoolRepository extends CassandraRepository<TransactionPool, String> {
    
}
