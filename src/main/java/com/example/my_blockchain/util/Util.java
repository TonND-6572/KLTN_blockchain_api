package com.example.my_blockchain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static byte[] concatenateByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static byte[][] separateByteArray(byte[] concatenatedArray, int lengthOfFirstArray) {
        byte[] firstArray = new byte[lengthOfFirstArray];
        byte[] secondArray = new byte[concatenatedArray.length - lengthOfFirstArray];
        
        System.arraycopy(concatenatedArray, 0, firstArray, 0, lengthOfFirstArray);
        System.arraycopy(concatenatedArray, lengthOfFirstArray, secondArray, 0, secondArray.length);
        
        return new byte[][] { firstArray, secondArray };
    }

    public static LocalDateTime parseTime(LocalDateTime time){
        return LocalDateTime.parse(time.format(formatter), formatter);
    }

    public static LocalDateTime parseTime(String time){
        return LocalDateTime.parse(time, formatter);
    }
}
