package com.example.my_blockchain.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.security.Signature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import com.example.my_blockchain.model.entity.Blockchain;
import com.example.my_blockchain.model.entity.UDT.Transaction;
public class BlockchainUtil {
    public static String getStringFromKey(Key key) {
        return CryptoLib.Encoded(key.getEncoded());
    }

    //********Public Key*********//

    public static String encryptPublicKey(PublicKey key){
        return getStringFromKey(key);
    } 

    public static PublicKey getPublicKey(String encodedPublicKey){
        PublicKey publicKey = null;
        try {
            byte[] pk = CryptoLib.Decoded(encodedPublicKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(pk);
            publicKey = kf.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    //********Private Key*********//

    public static String[] encryptPrivateKey(String msg, PrivateKey key) throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //Initialize salt
        Random r = new SecureRandom();
        byte[] salt = new byte[8];
        r.nextBytes(salt);

        //Initialize vector
        byte[] vector = new byte[128/8];
        r.nextBytes(vector);

        //initialize variables
        String MsgToEncrypt = getStringFromKey(key);
        
        //Generating AES key
        Cipher ecipher = CryptoLib.createCipher(msg, salt, new IvParameterSpec(vector), Cipher.ENCRYPT_MODE);

        //encrypttion
        byte[] encrypted = ecipher.doFinal(MsgToEncrypt.getBytes());
        byte[] concat = Util.concatenateByteArrays(salt, vector);
        String salt_iv = CryptoLib.Encoded(concat);
        String encrypted_str = CryptoLib.Encoded(encrypted);

        return new String[] {salt_iv, encrypted_str};
    }

    public static PrivateKey getPrivateKey(String salt_iv, String encryptedPrivateKey, String userPin) throws InvalidKeySpecException {
        byte[] decode = CryptoLib.Decoded(salt_iv);
        byte[][] b_salt_iv = Util.separateByteArray(decode, 8);
        byte[] salt = b_salt_iv[0];
        byte[] vector = b_salt_iv[1];

        byte[] cipherText = CryptoLib.Decoded(encryptedPrivateKey);
        PrivateKey privateKey = null;
        try {
            Cipher de_cipher = CryptoLib.createCipher(userPin, salt, new IvParameterSpec(vector), Cipher.DECRYPT_MODE);
            byte[] privKey = de_cipher.doFinal(cipherText);
            byte[] pk = CryptoLib.Decoded(new String(privKey));
            KeyFactory kf = KeyFactory.getInstance("EC");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pk);
            privateKey = kf.generatePrivate(keySpec);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /* Apply and verify signature */
    
    /**
     * Using privateKey and input data to generate ECDSA signature using Elliptic Curve algorithm
     * @param privateKey
     * @param data
     * @return realSig
     */
    public static String applySignature(PrivateKey privateKey, String data) {
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
        return CryptoLib.Encoded(realSig);
    }

    /**
     * Verify the signature is valid or not
     * @param publicKey
     * @param data
     * @param signature
     * @return boolean
     */
    public static boolean verifySignature(PublicKey publicKey, String data, String signature) {
        // signature.toString();
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(CryptoLib.Decoded(signature));
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    /**
     * Create genesis block
     * @return Block
     */
    public static Blockchain genesis() {
        LocalDateTime timeStamp = LocalDateTime.now();
        String lastHash = "";
        int difficulty = Configuration.DIFFICULTY;
        long nonce = 0;
        List<Transaction> transactions = new ArrayList<>();
        String merkleRoot = "";
        String hash = Blockchain.calculateHash(Timestamp.valueOf(timeStamp).getTime(), lastHash, difficulty, nonce, merkleRoot);
        
        return new Blockchain(timeStamp, lastHash, hash, nonce, difficulty, transactions, merkleRoot);
    }


    public static String getMerkleRoot(List<Transaction> transactions) {
        String[] txArrays = new String[transactions.size()];
        for (int i = 0; i < transactions.size(); i++) {
             txArrays[i]= transactions.get(i).getId().toString();
        }
        return new MerkleTree(txArrays).getRoot().getHash();
    }
}
