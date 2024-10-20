package com.demo.vwap_calculator.repository;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.entity.PriceDataEntity;

@DataJpaTest
public class PriceDataRepositoryTest {
	    @Autowired
	    private PriceDataRepository priceDataRepository;

	    private PriceData priceData;
	    private PriceDataEntity priceDataEntity;
	    
	    @BeforeEach
	    void setUp() {
	    	priceData  = PriceData.builder()
				     .entryNumber(1)
				     .currencyPair("AUD/USD")
				     .timeStamp("05:03 AM")
				     .price(0.342)
				     .volume(3454)
				     .build();
	             

	       priceDataEntity= PriceDataEntity.builder()
	                .entryNumber(priceData.getEntryNumber())
	                .currencyPair(priceData.getCurrencyPair())
	                .timeStamp(priceData.getTimeStamp())
	                .price(priceData.getPrice())
	                .volume(priceData.getVolume())
	                .build();
	        
	        // Save the employee to the repository to set up test data
	       priceDataRepository.save(priceDataEntity);
	       
	       
	    }
	    
	    @Test
	    @DisplayName("Test repository is not empty before saving any priceData")
	    public void testingToCheckRepositoryIsNotEmptyBeforeSaving() {
	    	priceDataRepository.findAll(); // Ensure the repository is empty
	        assertFalse(priceDataRepository.findAll().isEmpty());
	    }
}
