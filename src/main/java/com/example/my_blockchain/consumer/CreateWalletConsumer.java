package com.example.my_blockchain.consumer;

import com.example.my_blockchain.consumer.dto.TransactionEventDTO;
import com.example.my_blockchain.consumer.dto.WalletDTO;
import com.example.my_blockchain.model.mapper.WalletMapper;
import com.example.my_blockchain.service.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateWalletConsumer {
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @RabbitListener(queues = {"${rabbitmq.create-wallet-queue}"})
    public void receiveMessage(WalletDTO notificationRabbitDto) {
      log.info(notificationRabbitDto.getAddress());
      walletService.createWallet(walletMapper.toWallet(notificationRabbitDto));
    }
}
