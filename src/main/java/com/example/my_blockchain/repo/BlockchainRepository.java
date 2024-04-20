package com.example.my_blockchain.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.CompositeKey.BlockchainKey;

@Repository
public interface BlockchainRepository extends CassandraRepository<Blockchain, BlockchainKey>{
    
}
