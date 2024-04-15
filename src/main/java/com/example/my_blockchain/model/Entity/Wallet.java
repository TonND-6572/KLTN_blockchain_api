package com.example.my_blockchain.model.Entity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.Util.BlockchainUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Table(value = "wallet")
public class Wallet {
    @PrimaryKey
    private String address;
    private String secret;
    private String salt_iv;

    private List<Transaction> transactions;

    @Transient
    private PrivateKey private_key;

    @Transient
    private PublicKey public_key;

    public Wallet(){
        this.genKeyPair();
    }

    public Wallet(String address, String secret, String salt_iv, List<Transaction> transactions){
        this.address = address;
        this.secret = secret;
        this.salt_iv = salt_iv;
        this.transactions = transactions;
        try {
            this.private_key = BlockchainUtil.getPrivateKey(this.salt_iv, this.secret, this.address);
            this.public_key = BlockchainUtil.getPublicKey(this.address);
        } catch (Exception e){
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

    /**
     * Using privateKey and input data to generate ECDSA signature using Elliptic Curve algorithm
     * @param privateKey
     * @param data
     * @return realSig
     */
    public static byte[] applySignature(PrivateKey privateKey, String data) {
        Signature signature;
        byte[] realSig;
        try {
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);
            byte[] strByte = data.getBytes();
            signature.update(strByte);
            realSig = signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realSig;
    }

    /**
     * Verify the signature is valid or not
     * @param publicKey
     * @param data
     * @param signature
     * @return boolean
     */
    public static boolean verifySignature(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}