package com.demo.vwap_calculator.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;

@ExtendWith(MockitoExtension.class)
public class PriceDataTest {

	 private static PriceData priceData;
	 @BeforeAll
	 static void setup() {
		 priceData = PriceData.builder()
			     .entryNumber(1)
			     .currencyPair("AUD/USD")
			     .timeStamp("05:03")
			     .price(0.342)
			     .volume(3454)
			     .build();
	 }
	 
	 @Test
	 @DisplayName("Test Should pass if the field values contain the correct values accroding to their data type and every filed must have some value")
	 void getPriceDataValue_returnCorrectDataType() {
		 
		 assertNotNull(priceData.getCurrencyPair());
		 assertNotNull(priceData.getTimeStamp());
		 assertNotNull(priceData.getPrice());
		 assertNotNull(priceData.getVolume());
		 assertEquals("AUD/USD",priceData.getCurrencyPair() );
		 assertEquals(0.342,priceData.getPrice());
		 assertNotEquals("",priceData.getPrice());
		 assertEquals(3454,priceData.getVolume());
	}
	 
	 @Test
	    @DisplayName("Test with zero price valuee")
	    void gettingPriceData_correctValue_notZero() {
	        assertFalse(priceData.getPrice() == 0, "price should not be zero");
	    }
	 
	 @Test
	    @DisplayName("Test with incorrect CurrencyPair")
	    void shouldGetIncorrectCurrencyValues_PriceData() {
	       
	        assertFalse(priceData.getCurrencyPair().equals("ADE/092"), "Currency pair should be a string only");
	    }
	 
}
