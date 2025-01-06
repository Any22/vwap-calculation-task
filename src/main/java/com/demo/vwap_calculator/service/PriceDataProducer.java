package com.demo.vwap_calculator.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.vwap_calculator.dto.PriceData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceDataProducer {
	
	private final RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;

    public void sendPriceData(PriceData priceData) {
        rabbitTemplate.convertAndSend(queueName, priceData);
        log.info("Sent price data to queue: {}", priceData);
    }

}
