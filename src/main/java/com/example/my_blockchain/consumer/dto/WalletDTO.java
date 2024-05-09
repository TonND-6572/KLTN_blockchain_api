package com.example.my_blockchain.consumer.dto;

import com.example.my_blockchain.model.entity.Enum.WalletType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WalletDTO {
    private String address;
    private String secret;
    private String saltIv;
    private String code;
    private WalletType type;
}
