package com.example.my_blockchain.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.Wallet;

@Repository
public interface WalletRepository extends CassandraRepository<Wallet, String>{
    Wallet findByAddress(String address);
    List<Wallet> findAll();
}
