package com.demo.vwap_calculator.repository;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.service.PriceDataProducer;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Slf4j
@Repository
public class ExternalAPICallRepository {
    @Value("${external.api.endpoint}")
    private String externalApiPath;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Inject
    private PriceDataProducer priceDataProducer;

   public Flux<PriceData> getAllDataFromExternalAPI(){
       WebClient webClient = webClientBuilder.build();
       log.debug("executing getAllDataFromExternalAPI() ");
           return webClient.get()
                   .uri(externalApiPath)
                   .retrieve()
                   .bodyToFlux(PriceData.class)
                   .flatMap(data -> priceDataProducer.sendMessage(data).thenReturn(null));

           }

}



