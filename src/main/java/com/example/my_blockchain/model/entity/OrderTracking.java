package com.example.my_blockchain.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.model.entity.Enum.TransactionStatus;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(value = "order_tracking")
public class OrderTracking {
    @PrimaryKeyColumn(name = "order_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    Long orderId;    

    @PrimaryKeyColumn(name = "created_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    LocalDateTime createdTime;
    
    TransactionStatus status;
    String sender;
    String receiver;

    @Column("receiver_name")
    String receiverName;
}
