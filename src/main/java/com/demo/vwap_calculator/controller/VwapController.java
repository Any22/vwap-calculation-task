package com.demo.vwap_calculator.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.concurrent.Callable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.service.VwapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces=APPLICATION_JSON_VALUE)
public class VwapController {
	
	private final VwapService vwapService;
	
	@GetMapping("/get-data")
    public Callable<ResponseEntity<PriceDataResponse>> getCustomers() {

        return new Callable<ResponseEntity<PriceDataResponse>>() {
            @Override
            public ResponseEntity<PriceDataResponse> call() throws Exception {
            	PriceDataResponse response = vwapService.getPriceData();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        };
    }

}
