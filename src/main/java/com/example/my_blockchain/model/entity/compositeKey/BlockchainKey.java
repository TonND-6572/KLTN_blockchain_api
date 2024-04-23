package com.example.my_blockchain.model.entity.compositeKey;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.*;

@PrimaryKeyClass
@Data
public class BlockchainKey implements Serializable{
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID uuid = BlockchainUtil.generateUUID();
    
    @PrimaryKeyColumn(name = "created_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CreatedDate
    private LocalDateTime created_time = LocalDateTime.now();

    @PrimaryKeyColumn()
    private Integer year = created_time.getYear();
}
