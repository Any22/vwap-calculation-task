package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Pageable page = PageRequest.of(optionalRequest.getPage(),optionalRequest.getPageSize());
	
		
		for (PriceDataEntity price : priceDataRepository.findAll(page)) {
			priceDataList.add( new PriceData(price.getEntryNumber(),price.getTimeStamp(),price.getCurrencyPair()
					,price.getPrice(), price.getVolume()));
		}
		
		log.info("The PriceDTO is {}" + priceDataList);
	  PriceResponse priceDataResponse = this.calculateHourlyVwap(priceDataList);

	return priceDataResponse;
			
	}

//	private PriceData maptoDTO(PriceDataEntity pd) {
//
//		return PriceData.builder().entryNumber(pd.getEntryNumber()).timeStamp(pd.getTimeStamp())
//				.currencyPair(pd.getCurrencyPair()).price(pd.getPrice()).volume(pd.getVolume()).build();
//
//	}

	 public PriceResponse calculateHourlyVwap(List<PriceData> priceDataList) {
			List<PriceDataResponse> vwapList = new ArrayList<>();

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
		return 	PriceResponse.builder().priceDataResponse(vwapList).build();
			

		}
}
