package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataRequestOptional;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;

import com.demo.vwap_calculator.repository.PriceDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VwapService {


	private final PriceDataRepository priceDataRepository;

	public PriceResponse getPriceData(PriceDataRequestOptional optionalRequest) {
		 
		log.info("Getting all Data from repository.It may take some time ..!");
		
		List<PriceData> priceDataList = new ArrayList<>();
		log.info("page size is {}", optionalRequest.getPageSize().toString() );
           Pageable pageable = PageRequest.of(0,optionalRequest.getPageSize());
        	
			for (PriceDataEntity price : priceDataRepository.findAll(pageable)) {
				priceDataList.add( new PriceData(price.getEntryNumber(),price.getTimeStamp(),price.getCurrencyPair()
						,price.getPrice(), price.getVolume()));
				log.debug("page from repository containing data {}",price);
			}
		
			log.info("The PriceDTO is {}", priceDataList);
	
		PriceResponse priceDataResponse = this.calculateHourlyVwap(priceDataList,optionalRequest.getPageSize() );
		
		return priceDataResponse;
	}
	


//	private PriceData maptoDTO(PriceDataEntity pd) {
//
//		return PriceData.builder().entryNumber(pd.getEntryNumber()).timeStamp(pd.getTimeStamp())
//				.currencyPair(pd.getCurrencyPair()).price(pd.getPrice()).volume(pd.getVolume()).build();
//
//	}
	
	public PriceResponse getPriceDataUnPaged() {
		
        log.info("Excecuting getPriceDataUnPager()");
		
		List<PriceData> priceDataList = new ArrayList<>();
		  
			for (PriceDataEntity price : priceDataRepository.findAll()) {
				priceDataList.add( new PriceData(price.getEntryNumber(),price.getTimeStamp(),price.getCurrencyPair()
						,price.getPrice(), price.getVolume()));
			}
		
			log.info("The PriceDTO is {}" + priceDataList);
	
		PriceResponse priceDataResponse = this.calculateHourlyVwap(priceDataList,0 );
		
		return priceDataResponse;
	
		
		
	}

	 public PriceResponse calculateHourlyVwap(List<PriceData> priceDataList , Integer pageSize) {
			List<PriceDataResponse> vwapList = new ArrayList<>();
			log.info("The page size inside calculateHourlyVWAP is{}", pageSize);

			Map<String, Map<Integer, List<PriceData>>> groupedData = priceDataList.stream()
					.collect(Collectors.groupingBy(pd -> pd.getCurrencyPair(), Collectors.groupingBy(PriceData::getHour)));

			groupedData.forEach((currencyPair, hourlyData) -> {
				hourlyData.forEach((hour, dataList) -> {

					double weightedPriceSum = dataList.stream().mapToDouble(pd -> pd.getPrice() * pd.getVolume()).sum();

					int totalVolume = dataList.stream().mapToInt(PriceData::getVolume).sum();

					double vwapCalculated = weightedPriceSum / totalVolume;
					
					vwapList.add(PriceDataResponse.builder().uniqueCurrencyPair(currencyPair).hourlyData(hour)
							.vwap(vwapCalculated).build());
					
				});
				
			});
			Integer totalPriceData = vwapList.size();
			return 	PriceResponse.builder().totalPriceData(vwapList.size()).totalPages(pageSize-1).priceDataResponse(vwapList).build();
			

		}



	public void savedData(PriceData priceData) {
		
		PriceDataEntity entity= PriceDataEntity.builder()
				.timeStamp(priceData.getTimeStamp())
				.currencyPair(priceData.getCurrencyPair())
				.price(priceData.getPrice())
				.volume(priceData.getVolume())
				.build();
		
		priceDataRepository.save(entity);
		
			
	}



	

	
}
