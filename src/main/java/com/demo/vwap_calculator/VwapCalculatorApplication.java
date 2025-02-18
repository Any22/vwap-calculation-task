package com.demo.vwap_calculator;

//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class VwapCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VwapCalculatorApplication.class, args);
	}

}
