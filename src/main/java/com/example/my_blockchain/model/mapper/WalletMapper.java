package com.example.my_blockchain.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.my_blockchain.consumer.dto.WalletDTO;
import com.example.my_blockchain.model.entity.Wallet;
import com.example.my_blockchain.model.response.WalletResponse;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletResponse toResponse(Wallet wallet); 
    List<WalletResponse> toResponse(List<Wallet> wallets);
    @Mapping(source = "saltIv", target = "salt_iv")
    @Mapping(source = "type", target = "wallet_type")
    Wallet toWallet (WalletDTO walletDTO);
}
