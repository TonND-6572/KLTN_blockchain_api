package com.example.my_blockchain.model.entity.UDT;


import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@UserDefinedType(value = "udt_input")
public class Input {
    private String address;
    private String signature;
}
