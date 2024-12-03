package com.demo.vwap_calculator.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import java.util.concurrent.Callable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.vwap_calculator.dto.PriceDataRequestOptional;
import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.service.VwapService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class VwapController {

	private final VwapService vwapService;

	@GetMapping(produces = APPLICATION_JSON_VALUE, value = "/get-data")
	public Callable<ResponseEntity<PriceResponse>> getExistingData(
			@Min(1) @RequestParam (value = "page", defaultValue ="1", required = false) Integer page,
			@Min(2) @Max(10)@RequestParam (value = "page_size", required = false) Integer pageSize) 
			throws Exception {
		
		final long start = System.currentTimeMillis();
		return new Callable<ResponseEntity<PriceResponse>>() {
			@Override
			public ResponseEntity<PriceResponse> call() throws Exception {
				try {
				    PriceDataRequestOptional optionalRequest = PriceDataRequestOptional.builder()
				    		.page(page)
				    		.pageSize(pageSize)
				    		.build();
					PriceResponse priceResponse = vwapService.getPriceData(optionalRequest);
					long duration=System.currentTimeMillis()-start;
					log.debug("time taken"+duration);
					return new ResponseEntity<>(priceResponse, HttpStatus.OK);
				} catch (Exception ex) {
					log.error(ex.getMessage());
					throw ex;
				}

			}

		};
			
	}

}
