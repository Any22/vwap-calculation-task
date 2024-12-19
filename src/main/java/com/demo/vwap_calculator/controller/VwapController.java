package com.demo.vwap_calculator.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataRequestOptional;
import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.service.VwapService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
public class VwapController {
	@Inject
	private VwapService vwapService;

	private static final Integer defaultSize = 2;

	@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE, value = "/get-data")
	public Callable<ResponseEntity<PriceResponse>> getExistingData(
			@RequestParam(value = "page_size", required = false) Integer pageSize) throws Exception {

		final long start = System.currentTimeMillis();
		return new Callable<ResponseEntity<PriceResponse>>() {
			@Override
			public ResponseEntity<PriceResponse> call() throws Exception {
				try {
					Integer pSize = ((pageSize == null)|| pageSize.equals(0)) ? defaultSize : pageSize;
					log.debug("The pagesize is "+pSize);
					PriceDataRequestOptional optionalRequest = PriceDataRequestOptional.builder().pageSize(pSize)
							.build();
					PriceResponse priceResponse = vwapService.getPriceData(optionalRequest);
					long duration = System.currentTimeMillis() - start;
					log.info("time taken" + duration);
					return new ResponseEntity<>(priceResponse, HttpStatus.OK);
				} catch (Exception ex) {
					log.error(ex.getMessage());
					throw ex;
				}

			}

		};

	}

	@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE, value = "/get-data-unpaged")
	public Callable<ResponseEntity<PriceResponse>> getExistingDataUnPaged() throws Exception {

		final long start = System.currentTimeMillis();
		return new Callable<ResponseEntity<PriceResponse>>() {
			@Override
			public ResponseEntity<PriceResponse> call() throws Exception {
				try {

					PriceResponse priceResponse = vwapService.getPriceDataUnPaged();
					long duration = System.currentTimeMillis() - start;
					log.debug("time taken" + duration);
					return new ResponseEntity<>(priceResponse, HttpStatus.OK);
				} catch (Exception ex) {
					log.error(ex.getMessage());
					throw ex;
				}

			}

		};

	}

	@RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE, value = "/create")
	public Callable<ResponseEntity<String>> createNewData(@RequestBody @Valid PriceData priceData) {

		long start = System.currentTimeMillis();
		return new Callable<ResponseEntity<String>>() {
			@Override
			public ResponseEntity<String> call() throws Exception {
				try {
					PriceData priceData = vwapService.savedData();
					return new ResponseEntity<>("done ", HttpStatus.CREATED);
				} catch (Exception ex) {
					log.error(ex.getMessage());
					throw ex;
				}
				

			}

		};

	}

}