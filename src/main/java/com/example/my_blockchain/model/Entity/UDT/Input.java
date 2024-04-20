package com.example.my_blockchain.model.entity.UDT;

import java.nio.ByteBuffer;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UserDefinedType(value = "udt_input")
public class Input {
    private String address;
    private ByteBuffer signature;
}
