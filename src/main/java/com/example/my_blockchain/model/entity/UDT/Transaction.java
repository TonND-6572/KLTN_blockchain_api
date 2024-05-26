package com.example.my_blockchain.model.entity.UDT;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@UserDefinedType(value = "udt_transaction")
public class Transaction implements Serializable{
    @Id
    @Default
    private UUID id = UUID.randomUUID();
    
    @CreatedDate
    @Column(value="created_time", isStatic = true)
    @Default
    private LocalDateTime createdTime = LocalDateTime.now();

    @Frozen
    private Input input;
    @Frozen
    private List<Output> outputs;

    public String toString(){
        return input.toString() + outputs.toString() + createdTime.toString();
    }

}
