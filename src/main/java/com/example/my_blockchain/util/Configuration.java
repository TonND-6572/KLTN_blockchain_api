package com.example.my_blockchain.util;

public class Configuration {
    public static Integer DIFFICULTY = 4;
    public static int MINE_RATE = 20 * Time.SEC;
    public static int MIN_DIFFICULTY = 2;

    public static class Time {
        public static final int SEC = 1000;
        public static final int MIN = 60 * SEC;
        public static final int HOUR = 60 * MIN;
        public static final int DAY = 24 * HOUR;
    }
}
