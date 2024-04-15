package com.example.my_blockchain.model.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletGetRequest {
    public String address;
}