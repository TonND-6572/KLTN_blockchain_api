package com.example.my_blockchain.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.my_blockchain.model.Entity.Wallet;
import com.example.my_blockchain.model.Response.WalletResponse;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletResponse toResponse(Wallet wallet); 
    List<WalletResponse> toResponse(List<Wallet> wallets);
}
