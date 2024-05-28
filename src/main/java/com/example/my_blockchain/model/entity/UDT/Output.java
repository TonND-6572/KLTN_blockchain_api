package com.example.my_blockchain.model.entity.UDT;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.example.my_blockchain.model.entity.Enum.*;

import lombok.*;

@UserDefinedType(value="udt_output")
@Data 
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class Output {
    String address;
    Order orders;

    @Column("receiver_name")
    String receiverName;
    @Column("transaction_id")
    Long transactionId;
    @Column("transaction_status")
    TransactionStatus transactionStatus; 
}    
