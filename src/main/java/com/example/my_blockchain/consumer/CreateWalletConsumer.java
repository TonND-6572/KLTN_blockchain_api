package com.example.my_blockchain.consumer;

import com.example.my_blockchain.consumer.dto.WalletDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateWalletConsumer {

    @RabbitListener(queues = {"${rabbitmq.create-wallet-queue}"})
    public void receiveMessage(WalletDTO notificationRabbitDto) {
      log.info(notificationRabbitDto.getAddress());
    }
}
