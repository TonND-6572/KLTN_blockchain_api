package com.example.my_blockchain.model.entity.UDT;

import java.util.List;

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
    @Column("receiver_name")
    String receiverName;
    Order orders;
    TransactionStatus transaction_status; 
}
