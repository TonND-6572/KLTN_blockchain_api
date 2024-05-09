package com.example.my_blockchain.model.entity;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import java.security.spec.ECGenParameterSpec;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
// import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.model.entity.Enum.WalletType;
import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.util.BlockchainUtil;

import lombok.*;

@Data
@AllArgsConstructor
@Table(value = "wallet")
public class Wallet {
    @PrimaryKeyColumn(name = "address", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String address;
    
    @PrimaryKeyColumn(name = "wallet_type", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private WalletType wallet_type;

    @PrimaryKeyColumn(name = "code", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String code;

    private String secret;
    private String salt_iv;

    @Frozen
    private List<Transaction> transactions;

    @Transient
    private PrivateKey private_key;

    @Transient
    private PublicKey public_key;

    public Wallet() {
        genKeyPair();
    }

    public Wallet(String address, String secret, String salt_iv, WalletType wallet_type, List<Transaction> transactions){
        this.address = address;
        this.secret = secret;
        this.salt_iv = salt_iv;
        this.wallet_type = wallet_type;
        this.transactions = transactions;
        try {
            this.private_key = BlockchainUtil.getPrivateKey(this.salt_iv, this.secret, this.address);
            this.public_key = BlockchainUtil.getPublicKey(this.address);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void genKey(){
        try {
            this.private_key = BlockchainUtil.getPrivateKey(this.salt_iv, this.secret, this.address);
            this.public_key = BlockchainUtil.getPublicKey(this.address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void genKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "SunEC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            this.private_key = keyPair.getPrivate();
            this.public_key = keyPair.getPublic();
            this.address = BlockchainUtil.encryptPublicKey(this.public_key);

            String[] encrypted = BlockchainUtil.encryptPrivateKey(this.address , this.private_key);
            this.salt_iv = encrypted[0];
            this.secret = encrypted[1];
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public String toString(){
        return "Wallet -" +
                "\n address: " + String.valueOf(this.getAddress()) +
                "\n secret: " + String.valueOf(this.getSecret()) +
                "\n transactions: " + String.valueOf(this.getTransactions()) + "\n";
    }

    
}