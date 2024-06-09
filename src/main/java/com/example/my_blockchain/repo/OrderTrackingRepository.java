package com.example.my_blockchain.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.my_blockchain.model.entity.OrderTracking;
import java.util.List;


@Repository
public interface OrderTrackingRepository extends CassandraRepository<OrderTracking, Long> {
    List<OrderTracking> findByOrderId(Long orderId);
}
