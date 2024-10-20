package com.demo.vwap_calculator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.service.VwapService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VwapController.class)
public class VwapControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private VwapService vwapService;

	@InjectMocks
	private VwapController vwapController;
	  
	private PriceData priceData1;
	
//	private PriceDataResponse priceDataResponse;
	
	List<PriceData> priceDataList = new ArrayList<>();
	
	 @BeforeEach
	  void setUp() {
		 priceData1  = PriceData.builder()
			     .entryNumber(1)
			     .currencyPair("AUD/USD")
			     .timeStamp("05:03 AM")
			     .price(0.342)
			     .volume(3454)
			     .build();
	 
		 priceDataList.add(priceData1);

  
	 }
	 
	 @Test
     void toTestForGettingPriceDataOrNot() throws Exception {
  	   
		
		    List<PriceDataResponse> mockPriceDataResponse = new ArrayList<>();
		    // Add mock response data to the list
		    mockPriceDataResponse.add( PriceDataResponse.builder()
		    		.uniqueCurrencyPair("AUD/USD")
		    		.hourlyData(5)
		    		.vwap(0.342)
		    		.build());
//		    mockPriceDataResponse.add(PriceDataResponse.builder.("AUD/USD",5,0.342));

		    when(vwapService.getPriceData()).thenReturn(priceDataList);
		    when(vwapService.calculateHourlyVwap(priceDataList)).thenReturn(mockPriceDataResponse);

		    mockMvc.perform(get("/get-data")
		              .accept(MediaType.APPLICATION_JSON))
		              .andExpect(status().isOk())
		              .andExpect(content().json(asJsonString(mockPriceDataResponse)));  // Check against expected response

		    assertEquals(priceDataList.get(0).getEntryNumber(), priceData1.getEntryNumber());
		    assertEquals(priceDataList.get(0).getCurrencyPair(), priceData1.getCurrencyPair());
		    assertEquals(priceDataList.get(0).getTimeStamp(), priceData1.getTimeStamp());
		    assertEquals(priceDataList.get(0).getPrice(), priceData1.getPrice());
		    assertEquals(priceDataList.get(0).getVolume(), priceData1.getVolume());
		    verify(vwapService, times(1)).getPriceData();
		    verify(vwapService, times(1)).calculateHourlyVwap(priceDataList);

		 }


	 //Helper method
	  private static String asJsonString(final Object obj) {
		    try {
		        return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }
		}
}
