package com.example.my_blockchain.util;

public class Configuration {
    public final static Integer DIFFICULTY = 5;
    public final static int MINING_SCHEDULE = 2 * Time.MIN;
    public final static int INITIAL_DELAY = 1 * Time.MIN;
    public final static int MINE_RATE = MINING_SCHEDULE +  20 * Time.SEC;
    public final static int MIN_DIFFICULTY = 3;
    public final static int MAX_TRANSACTION = 10;

    public static class Time {
        public final static int SEC = 1000;
        public final static int MIN = 60 * SEC;
        public final static int HOUR = 60 * MIN;
        public final static int DAY = 24 * HOUR;
    }
}
