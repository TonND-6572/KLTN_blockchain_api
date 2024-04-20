package com.example.my_blockchain.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignupRequest {
    String name;
    String password;
}
