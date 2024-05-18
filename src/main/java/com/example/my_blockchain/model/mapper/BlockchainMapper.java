package com.example.my_blockchain.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.response.BlockchainResponse;

@Mapper(componentModel = "spring")
public interface BlockchainMapper {
    @Mapping(source = "bk.uuid", target = "uuid")
    @Mapping(source = "bk.createdTime", target = "createdTime")
    @Mapping(target = "transactions", ignore = true)
    BlockchainResponse toResponse(Blockchain blockchain);
}
