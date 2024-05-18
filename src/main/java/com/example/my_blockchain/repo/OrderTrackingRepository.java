package com.example.my_blockchain.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.OrderTracking;

@Repository
public interface OrderTrackingRepository extends CassandraRepository<OrderTracking, Long> {
    
}
