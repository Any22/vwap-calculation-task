package com.demo.vwap_calculator.repository;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataList;
import com.demo.vwap_calculator.dto.PriceDataResponse;
//import com.demo.vwap_calculator.service.PriceDataProducer;
import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.service.PriceDataProducer;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Repository
public class ExternalAPICallRepository {
    @Value("${external.api.endpoint}")
    private String externalApiPath;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Inject
    private PriceDataProducer priceDataProducer;

    public void getAllDataFromExternalAPI() {
        WebClient webClient = webClientBuilder.build();
        log.debug("executing getAllDataFromExternalAPI() ");

        Mono<PriceDataList> priceDataResponseMono = webClient.get()
                .uri(externalApiPath)
                .retrieve()
                .bodyToMono(PriceDataList.class);

        //.subscribe is used here for asynchronous processing, non-blocking application.I als o helps in managing any
        // exception that might occur during the asynchronous operation s
        priceDataResponseMono.subscribe(priceDataResponse -> {
            List<PriceData> priceData = priceDataResponse.getPriceDataList();
            PriceDataList dataList = PriceDataList.builder().priceDataList(priceData).build();
            //sending the message to the queue
            priceDataProducer.sendMessage(dataList);
        });


    }

}

