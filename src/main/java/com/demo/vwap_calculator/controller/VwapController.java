package com.demo.vwap_calculator.controller;

import java.util.concurrent.Callable;

import com.demo.vwap_calculator.dto.*;
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

import com.demo.vwap_calculator.repository.PriceDataRepository;
//import com.demo.vwap_calculator.service.PriceDataProducer;
import com.demo.vwap_calculator.service.VwapService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// duplication of data is found after making the post request . Need to solve it
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class VwapController {

	private final VwapService vwapService;
	
//	private final PriceDataProducer priceDataProducer;

	private static final Integer defaultPageSize = 2;
	
	/******************************************************************************************************************
	 *  The price data is received and necessary calculations are being done which will be available to see through 
	 *  GET Method . The price data not need to be stored in data base for following reasons:
	 *  - Immediate processing : Since the data is processed as soon as it arrives , storing in RabbitMQ for short-term 
	 *  handling is suffice.
	 *  - Transient Data : If the data object is needed for calculation, not for future reference,overhead of database
	 *  storage can be avoided .
	 *  - Performance : Avoiding database storage can reduce the latency and improve the performance application
	 *  - Simplicity : Easier to maintain system 
	 *  Note : for auditing , compliance and data analysis , data can be stored in data base (it will be required)
	 *  
	 *
	 * @return ResponseEntity of String
	 ******************************************************************************************************************/
	

	

	@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE, value = "/get-data")
	public ResponseEntity<String> getExistingData(
			@RequestParam(value = "page_size", required = false) Integer pageSize) throws Exception {

		final long start = System.currentTimeMillis();

            try {
                Integer pSize = ((pageSize == null) || pageSize.equals(0)) ? defaultPageSize : pageSize;
                log.debug("The pagesize is " + pSize);
                PriceDataRequestOptional optionalRequest = PriceDataRequestOptional.builder().pageSize(pSize)
                        .build();
              //  PriceResponse priceResponse = vwapService.getPriceData(optionalRequest);
				vwapService.getPriceData();
                long duration = System.currentTimeMillis() - start;
                log.info("time taken" + duration);
                return new ResponseEntity<>("created", HttpStatus.OK);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw ex;
            }


	}
	
	//to be implemented 

//	@RequestMapping(method = GET, consumes = APPLICATION_JSON_VALUE, value = "/get-data/{hour}")
//	public Callable<ResponseEntity<String>> getHourlyData(@PathVariable String timeInHours) {
//
//		long start = System.currentTimeMillis();
//		return new Callable<ResponseEntity<String>>() {
//			@Override
//			public ResponseEntity<String> call() throws Exception {
//				try {
//					PriceDataResponse priceDataResponseHourly= vwapService.getPriceDataByHour(timeInHours);
//					return new ResponseEntity<>(" The data has been created successfully ", HttpStatus.CREATED);
//				} catch (Exception ex) {
//					log.error(ex.getMessage());
//					throw ex;
//				}
//
//			}
//
//		};
//
//	}
	

	
	

}