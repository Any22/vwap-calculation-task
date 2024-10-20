package com.demo.vwap_calculator.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.service.VwapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VwapController {
	
	private final VwapService vwapService;
	
	@GetMapping(produces= APPLICATION_JSON_VALUE, value = "/get-data")
    public ResponseEntity<List> getExistingData() {     
		
		try {
            	List<PriceData> dataResponse = vwapService.getPriceData();
            	List<PriceDataResponse> priceDataResposne = vwapService.calculateHourlyVwap(dataResponse);
                return new ResponseEntity<List>(priceDataResposne, HttpStatus.OK);
		} catch (Exception ex) {
			   log.error(ex.getMessage());
	    	   throw ex;
		   }  
        
    }
	

}
