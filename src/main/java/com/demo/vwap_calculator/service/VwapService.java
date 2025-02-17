package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import com.demo.vwap_calculator.command.ExternalAPICommands;
import com.demo.vwap_calculator.repository.ExternalAPICallRepository;
import jakarta.inject.Inject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataRequestOptional;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.dto.PriceResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.entity.PriceDataResponseEntity;
import com.demo.vwap_calculator.exception.DuplicateRecordExist;
import com.demo.vwap_calculator.repository.PriceDataRepository;
import com.demo.vwap_calculator.repository.PriceDataResponseEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class VwapService {
	@Inject
	private PriceDataProducer priceDataProducer;

	@Inject
	private  PriceDataRepository priceDataRepository;

	@Inject
	private  PriceDataResponseEntityRepository priceDataResponseEntityRepository;

	@Inject
	private ExternalAPICallRepository repository;

	public void savedData(PriceData priceData) {

		priceDataProducer.sendMessage(priceData);

//		if (priceDataRepository.existsByCurrencyPairAndHour(priceData.getCurrencyPair(), priceData.getHour())) {
//
//			throw new DuplicateRecordExist("The record already exist....!!");
//		}
//
//		PriceDataEntity entity = PriceDataEntity.builder().timeStamp(priceData.getTimeStamp())
//				.currencyPair(priceData.getCurrencyPair()).price(priceData.getPrice()).volume(priceData.getVolume())
//				.hour(priceData.getHour()).build();



//		List<PriceData> priceDataList = new ArrayList<>();
//		priceDataList.add(priceData);
//
//		PriceResponse priceDataResponse = this.calculateHourlyVwap(priceDataList, 2);
//
//		this.saveTheCalculatedValues(priceDataResponse.getPriceDataResponse());
	}
	
	public PriceResponse calculateHourlyVwap(List<PriceData> priceDataList, Integer pageSize) {
		List<PriceDataResponse> vwapList = new ArrayList<>();
		log.info("The page size inside calculateHourlyVWAP is{}", pageSize);

		Map<String, Map<Integer, List<PriceData>>> groupedData = priceDataList.stream()
				.collect(Collectors.groupingBy(PriceData::getCurrencyPair, Collectors.groupingBy(PriceData::getHour)));

		groupedData.forEach((currencyPair, hourlyData) -> {
			hourlyData.forEach((hour, dataList) -> {

				double weightedPriceSum = dataList.stream().mapToDouble(pd -> pd.getPrice() * pd.getVolume()).sum();

				double totalVolume = dataList.stream().mapToDouble(PriceData::getVolume).sum();

				double vwapCalculated = weightedPriceSum / totalVolume;

				vwapList.add(PriceDataResponse.builder().uniqueCurrencyPair(currencyPair).hourlyData(hour)
						.vwap(vwapCalculated).build());

			});

		});

		return PriceResponse.builder().totalPriceData(vwapList.size()).totalPages(pageSize - 1)
				.priceDataResponse(vwapList).build();

	}

	protected void saveTheCalculatedValues(List<PriceDataResponse> vwapList) {

		for (PriceDataResponse priceData : vwapList) {
			PriceDataResponseEntity dataResponseEntity = PriceDataResponseEntity.builder()
					.uniqueCurrencyPair(priceData.getUniqueCurrencyPair()).hourlyData(priceData.getHourlyData())
					.vwap(priceData.getVwap()).build();
			priceDataResponseEntityRepository.save(dataResponseEntity);
		}

	}


//	public PriceResponse getPriceData(PriceDataRequestOptional optionalRequest) {
//
//		log.info("Executing getPriceData() with optionalRequest {}", optionalRequest.getPageSize().toString());
//
//		List<PriceDataResponse> priceDataList = new ArrayList<>();
//
//		Pageable pageable = PageRequest.of(0, optionalRequest.getPageSize());
//
//		for (PriceDataResponseEntity price : priceDataResponseEntityRepository.findAll(pageable)) {
//
//			priceDataList
//					.add(new PriceDataResponse(price.getUniqueCurrencyPair(), price.getHourlyData(), price.getVwap()));
//
//			log.debug("page from repository containing data {} ", price);
//		}
//
//		log.info("The PriceDataResposne is {}", priceDataList);
//	Integer pageSize =( optionalRequest.getPageSize()  > priceDataList.size() ) ? priceDataList.size()-1 : optionalRequest.getPageSize()-1;
//
//		return PriceResponse.builder().currentPage(pageSize).totalPriceData(priceDataList.size())
//				.priceDataResponse(priceDataList).build();
//
//	}


	public Flux<PriceData> getPriceData() {

		log.info("Executing getPriceData()");
         return   repository.getAllDataFromExternalAPI();

	}



}