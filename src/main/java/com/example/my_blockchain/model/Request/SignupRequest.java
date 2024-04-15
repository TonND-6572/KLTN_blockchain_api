package com.example.my_blockchain.model.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignupRequest {
    String name;
    String password;
}
