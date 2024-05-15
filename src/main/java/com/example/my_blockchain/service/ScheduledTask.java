package com.example.my_blockchain.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.util.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {
    private final BlockchainService blockchainService;

    /*
     * 2 phút blockchain sẽ mining một lần
     * Delay chạy lần đầu 1 phút
     */
    @Async
    @Scheduled(fixedDelay = Configuration.Time.MIN*2, initialDelay = Configuration.Time.MIN*1)
    public void scheduledMine(){
        log.info("----- start mining ----");
        BlockchainResponse block = blockchainService.startMine();
        if (block == null){
            log.info("No transactions to mine");
        }
        log.info("----- finish mining ----");
    }
}
