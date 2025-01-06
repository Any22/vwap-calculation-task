package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceDataConsumer {

	 private final VwapService vwapCalculator;

	    private final Map<String, List<PriceData>> priceDataMap = new ConcurrentHashMap<>();

	    @RabbitListener(queues = "${queue.name}")
	    public void consumePriceData(PriceData priceData) {
	        log.info("Received price data: {}", priceData);

	        String currencyPair = priceData.getCurrencyPair();
	        int hour = priceData.getHour();

	        priceDataMap.computeIfAbsent(currencyPair + ":" + hour, key -> new ArrayList<>()).add(priceData);

	        // Calculate VWAP for the current hour
	        List<PriceData> hourlyData = priceDataMap.get(currencyPair + ":" + hour);
	        PriceResponse priceResponse = vwapCalculator.calculateHourlyVwap(hourlyData,0);
	        log.info("VWAP for {} at hour {}: {}", currencyPair, hour, priceResponse);

	        // Persist VWAP in database (optional)
	       
	        //vwapCalculator.saveTheCalculatedValues(priceResponse.getPriceDataResponse());
	    }
}
