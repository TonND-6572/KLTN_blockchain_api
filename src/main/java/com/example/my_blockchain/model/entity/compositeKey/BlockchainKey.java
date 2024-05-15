package com.example.my_blockchain.model.entity.compositeKey;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import com.example.my_blockchain.util.BlockchainUtil;

import lombok.*;

@PrimaryKeyClass
@Data
public class BlockchainKey implements Serializable{
    @PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID uuid = BlockchainUtil.generateUUID();
    
    @PrimaryKeyColumn(name = "created_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CreatedDate
    private LocalDateTime created_time = LocalDateTime.now();
    
    @PrimaryKeyColumn(name = "year", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Integer year = created_time.getYear();

    public String toString(){
        return 
            "UUID: " + uuid.toString() + 
            "\nYear: " + year +
            "\nCreated Time: " + created_time;
    }
}
