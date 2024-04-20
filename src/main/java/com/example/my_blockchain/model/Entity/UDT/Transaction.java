package com.example.my_blockchain.model.entity.UDT;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Builder
@UserDefinedType(value = "udt_transaction")
public class Transaction implements Serializable{
    @CassandraType(type = CassandraType.Name.UUID)
    @Builder.Default
    private UUID id = Uuids.random();
    
    @CreatedDate
    @Column(value="created_time", isStatic = true)
    @Builder.Default
    private Date created_time = new Date();

    @Frozen
    private Input input;
    @Frozen
    private Output outputs;
}
