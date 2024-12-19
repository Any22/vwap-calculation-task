package com.demo.vwap_calculator.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.service.VwapService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VwapControllerTest {

	@InjectMocks
	private VwapController vwapController;
	
	@Mock
	private VwapService vwapService;


	@BeforeEach
	public void setUp() {
		
		//when(vwapService.getPriceData(any())).thenReturn(new PriceResponse(new ArrayList<>()));

	}

	@Test
	public void getExistingData_retrunSucess() throws Exception {
		ResponseEntity<PriceResponse> response = vwapController.getExistingData(23).call();
	    //assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertAll("getExistingData_retrunSucess", 
				() -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
				() -> Assertions.assertNotNull(response.getBody()));
	}
	
//	@Test
//	public void createNewData_ReturnSuccess() {
//		 vwapService.savedData(priceData);
//	}
}
