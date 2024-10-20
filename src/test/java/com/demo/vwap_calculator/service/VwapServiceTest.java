package com.demo.vwap_calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.repository.PriceDataRepository;

@ExtendWith(MockitoExtension.class)
public class VwapServiceTest {
	
	@InjectMocks
	private VwapService vwapService;

    @Mock
    
    private PriceDataRepository priceDataRepository;
    
    private PriceData priceData1;
	
	private PriceDataEntity priceDataEntity1;
	
	@BeforeEach
    void setUp() {
		 priceData1  = PriceData.builder()
			     .entryNumber(1)
			     .currencyPair("AUD/USD")
			     .timeStamp("05:03 AM")
			     .price(0.342)
			     .volume(3454)
			     .build();
        
             
		 priceDataEntity1 = PriceDataEntity.builder()
				 .entryNumber(priceData1.getEntryNumber())
				 .currencyPair(priceData1.getCurrencyPair())
				 .timeStamp(priceData1.getTimeStamp())
				 .price(priceData1.getPrice())
				 .volume(priceData1.getVolume())
				 .build();
        
	}  
	
	@Test
	public void get_PriceData(){
		 List<PriceDataEntity> priceDataEntityExpectedList = Arrays.asList(priceDataEntity1);
			
	        // Mocking the repository response
	        when(priceDataRepository.findAll()).thenReturn(priceDataEntityExpectedList);

	        // Fetch employees via service
	     
	        List<PriceData> priceDataEntityActualList= vwapService.getPriceData();
	        
	        assertNotNull(priceDataEntityExpectedList);
	        assertEquals(1,priceDataEntityExpectedList .size(), "The size of the employee list should be 1");
            assertEquals(priceDataEntityExpectedList.get(0).getCurrencyPair(), priceDataEntityActualList.get(0).getCurrencyPair());
	        

	}

}
