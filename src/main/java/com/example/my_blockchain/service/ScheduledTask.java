package com.example.my_blockchain.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.my_blockchain.model.response.BlockchainResponse;
import com.example.my_blockchain.repo.BlockchainRepository;
import com.example.my_blockchain.util.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {
    private final BlockchainRepository blockchainRepository;
    private final BlockchainService blockchainService;

    /*
     * 2 phút blockchain sẽ mining một lần
     * Delay chạy lần đầu 1 phút
     */
    @Async
    @Scheduled(fixedDelay = Configuration.MINING_SCHEDULE, initialDelay = Configuration.INITIAL_DELAY)
    public void scheduledMine(){
        try{
            log.info("----- start scheduled mining ----");
            // log.info("----- checking blockchain ----");
            // List<Blockchain> inValidBlocks = blockchainService.checkBlockchain();
            // if (inValidBlocks.size() > 0){
            //     log.info("Invalid block at ", inValidBlocks.get(0).getBk().getUuid());
            //     return;
            // }
            // log.info("----- finish checking blockchain ----");
            log.info("----- start mining ----");
            if (blockchainRepository.findAll().size() > 0){
                BlockchainResponse block = blockchainService.startMine();
                if (block == null){
                    log.info("No transactions to mine");
                }
            }
            else{
                log.info("there is no genesis block");
            }
            log.info("----- finish mining ----");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
