package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.repository.PriceDataRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VwapService {
	
	private final PriceDataRepository priceDataRepository ;
	
	public PriceDataResponse getPriceData() {
		log.info("Getting all Data from repository...!");
		List<PriceDataEntity> priceDataEntityList= priceDataRepository.findAll();
		List<PriceData> priceDataDto = this.maptoDTO(priceDataEntityList);
		
		return PriceDataResponse.builder().priceData(priceDataDto).build();
	

	}

	private List<PriceData> maptoDTO(List<PriceDataEntity> priceDataEntityList) {
		List<PriceData> priceDataDTO = new ArrayList<>();
		priceDataEntityList.stream()
		                   .forEach(entity-> 
		                                    priceDataDTO.add(new PriceData( entity.getEntryNumber(),
		                                            		                entity.getTimeStamp(), 
		                                            		                entity.getCurrencyPair(),
		                                            		                entity.getPrice(), 
		                                            		                entity.getVolume()
		                                            		               )
				                                             )
		                            );
		
		priceDataDTO.stream().forEach(System.out::println);
		
		return priceDataDTO;
	}

}
