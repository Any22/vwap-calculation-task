package com.demo.vwap_calculator.service;

import com.demo.vwap_calculator.dto.PriceData;
import jakarta.inject.Inject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceDataProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	@Inject
	private RabbitTemplate rabbitTemplate;

	public Mono<Void> sendMessage(PriceData priceData) {

		log.info("message sent {}", priceData.toString());
		rabbitTemplate.convertAndSend(exchange, routingKey, priceData);
		return Mono.empty();
	}

}
