package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.demo.vwap_calculator.dto.PriceDataList;
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

	    @RabbitListener(queues = "${rabbitmq.queue.name}")
	    public void consumePriceData(PriceDataList priceData) {
	        log.info("Received price data for consumption: {}", priceData);

            for (PriceData data: priceData.getPriceDataList()) {

                String currencyPair = data.getCurrencyPair();
                int hour = data.getHour();

                priceDataMap.computeIfAbsent(currencyPair + ":" + hour, key -> new ArrayList<>()).add(data);

                // Calculate VWAP for the current hour
                List<PriceData> hourlyData = priceDataMap.get(currencyPair + ":" + hour);

                PriceResponse priceResponse = vwapCalculator.calculateHourlyVwap(hourlyData, 0);
                log.info("VWAP for {} at hour {}: {}", currencyPair, hour, priceResponse);

                // Persist VWAP in database (optional)
                vwapCalculator.saveTheCalculatedValues(priceResponse.getPriceDataResponse());
            }

	    }
}
