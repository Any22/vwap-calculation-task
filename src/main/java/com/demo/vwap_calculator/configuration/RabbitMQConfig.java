package com.demo.vwap_calculator.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


	@Value("${rabbitmq.queue.name}")
	private String queue;

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.key}")
	private String routingJsonKey;

	//spring bean for queue (store JSON messages),durable -> true means it will be persisted after
	// Rabbit MQ will be restarted
	@Bean
	public Queue queue() {
		return new Queue(queue, true, false, false);
	}

	//spring bean for rabbitMQ exchange
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	// Binding between JSON queue and exchange using routing key
	@Bean
	public Binding jsonBinding() {

		return BindingBuilder
				.bind(queue())
				.to(exchange())
				.with(routingJsonKey);
	}

	/*************************************************************************************************************
	 Spring boot auto-configuration provides the following beans:
	 - Connection factory
	 - Rabbit Template
	 - Rabbit Admin
	 **************************************************************************************************************/


//configured MessageConverter to RabbitTemplate for JSON serialize and de-serialize.
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate ampqTemplate(ConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}
