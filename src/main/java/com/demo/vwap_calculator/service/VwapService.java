package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.repository.PriceDataRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class VwapService {
	
	private PriceDataRepository priceDataRepository ;
	
	public PriceDataResponse getPriceData() {
		log.info("Getting all Data from repository...!");
		List<PriceDataEntity> priceDataEntityList= priceDataRepository.findAll();
		
		//convert into DTO
		return null;

	}

}
