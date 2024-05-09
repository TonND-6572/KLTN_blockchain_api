package com.example.my_blockchain.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.my_blockchain.model.entity.UDT.Transaction;
import com.example.my_blockchain.model.entity.compositeKey.BlockchainKey;
import com.example.my_blockchain.util.BlockchainUtil;
import com.example.my_blockchain.util.Configuration;
import com.example.my_blockchain.util.CryptoLib;
import com.example.my_blockchain.util.MerkleTree;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(value="blockchain_by_year") //change blockchain -> blockchain_by_year (Cuz cassandra sorting suck :DDD)
public class Blockchain {
    @PrimaryKey
    @Builder.Default
    private BlockchainKey bk = new BlockchainKey();

    private String hash;
    private Long nonce;
    private String previous_hash;
    private Integer difficulty;
    private String merkle_root;
    
    private List<Transaction> transactions;

    public Blockchain(LocalDateTime timeStamp, String lastHash, String hash, Long nonce, Integer difficulty, List<Transaction> transactions, String merkleRoot){
        BlockchainKey bk = new BlockchainKey();
        bk.setCreated_time(timeStamp);
        bk.setUuid(BlockchainUtil.generateUUID());
        bk.setYear(timeStamp.getYear());
        this.bk = bk;
        this.previous_hash = lastHash;
        this.hash = hash;
        this.nonce = nonce;
        this.difficulty = difficulty;
        this.merkle_root = merkleRoot;
        this.transactions = transactions;
    }

    /*
     * Calculate the hash of the block
     */
    public void caclulateHash() {
        this.hash =  CryptoLib.getSHA256Hash(
                this.bk.getUuid().toString() 
                + Long.toString(Timestamp.valueOf(this.bk.getCreated_time()).getTime()) 
                + Long.toString(this.nonce)
                + this.merkle_root
                + this.previous_hash
                + Long.toString(this.difficulty)
            );
    }

    /*
     * Calculate the hash of the block
     */
    public static String calculateHash(Long timeStamp, String previous_hash, int difficulty, Long nonce, String merkle_root) {
        return CryptoLib.getSHA256Hash(
                timeStamp.toString()
                + previous_hash
                + Long.toString(difficulty)
                + nonce
                + merkle_root
            );
    }

    /**
     * Mining block
     * @param lastBlock
     * @param transactions
     * @return Block
     */
    public static Blockchain mineBlock(Blockchain lastBlock, List<Transaction> transactions) {
        LocalDateTime timeStamp = LocalDateTime.now();
        long nonce = 0;
        String lastHash = lastBlock.getHash();
        int difficulty = lastBlock.getDifficulty();
        String merkleRoot = BlockchainUtil.getMerkleRoot(transactions);
        String hash = "";
        while (hash.length() < difficulty || !hash.substring(0, difficulty).equals(new String(new char[difficulty]).replace('\0', '0'))) {
            timeStamp = LocalDateTime.now();
            difficulty = Blockchain.adjustDifficulty(lastBlock, Timestamp.valueOf(timeStamp).getTime());
            hash = Blockchain.calculateHash(Timestamp.valueOf(timeStamp).getTime(), lastHash, difficulty, nonce, merkleRoot);
            nonce++;
        }
        return new Blockchain(timeStamp, lastHash, hash, nonce - 1, difficulty, transactions, merkleRoot);
    }

    /**
     * Adjust mining difficulty according to control the mining speed
     * @param lastBlock
     * @param currentTime
     * @return
     */
    //TODO: Fix the hardcoded mine rate
    public static int adjustDifficulty(Blockchain lastBlock, long currentTime) {
        int difficulty = lastBlock.getDifficulty();
        difficulty = Timestamp.valueOf(lastBlock.getBk().getCreated_time()).getTime() + Configuration.MINE_RATE > currentTime ? ++difficulty : --difficulty;
        return difficulty < Configuration.MIN_DIFFICULTY ? Configuration.MIN_DIFFICULTY : difficulty;
    }

    public String toString() {
        return bk.toString();
    }
}
