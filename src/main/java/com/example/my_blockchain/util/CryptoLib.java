package com.example.my_blockchain.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoLib {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getSHA256Hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));

            return toHex(hash); 
        } catch (Exception NoSuchAlgorithmException) {
            throw new RuntimeException(NoSuchAlgorithmException);
        }
    }

    public static String Encoded(byte[] msg){
        return Base64.getEncoder().encodeToString(msg);
    }

    public static byte[] Decoded(String msg){
        return Base64.getDecoder().decode(msg);
    }

    public static Cipher createCipher(String msg, byte[] salt, IvParameterSpec ivspec, int mode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher ecipher;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec mykeySpec = new PBEKeySpec(msg.toCharArray(), salt, 10000, 128);
        SecretKey tmp = factory.generateSecret(mykeySpec);
        SecretKeySpec mySecretkey = new SecretKeySpec(tmp.getEncoded(), "AES");

        //==> Create and initiate encryption
        System.out.println("Initiate encryption alogrithm... + " + mode);
        ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(mode, mySecretkey, ivspec);
    
        return ecipher;
    }
}