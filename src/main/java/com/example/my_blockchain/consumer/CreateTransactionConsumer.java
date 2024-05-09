package com.example.my_blockchain.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.my_blockchain.consumer.dto.TransactionEventDTO;
import com.example.my_blockchain.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTransactionConsumer {
  private final TransactionService transactionService;

  @RabbitListener(queues = {"${rabbitmq.create-transaction-queue}"})
  public void receiveMessage(TransactionEventDTO notificationRabbitDto) {
    log.info("Create transaction from address: {}", notificationRabbitDto.getSenderAddress());
    transactionService.createTransaction(notificationRabbitDto);
  }
}
