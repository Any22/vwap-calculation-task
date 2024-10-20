package com.demo.vwap_calculator.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;

@ExtendWith(MockitoExtension.class)
public class PriceDataTest {

	 private PriceData priceData;
	 
	 
	 @Test
	 @DisplayName("Test Should pass if the field values contain the correct values accroding to their data type and every filed must have some value")
	 void shouldGetTheCorrectValues_PriceData() {
		 
		 priceData = PriceData.builder()
				     .entryNumber(1)
				     .currencyPair("AUD/USD")
				     .timeStamp("05:03")
				     .price(0.342)
				     .volume(3454)
				     .build();
		 
		 assertNotNull(priceData.getCurrencyPair());
		 assertNotNull(priceData.getTimeStamp());
		 assertNotNull(priceData.getPrice());
		 assertNotNull(priceData.getVolume());
		 assertEquals("NZD/JPY",priceData.getCurrencyPair() );
		 assertEquals(45,empDTO.getAge());
		 assertNotEquals("",empDTO.getAge());
		 assertEquals("ACC",empDTO.getDeptName());
		
		 
	
     

}
}
