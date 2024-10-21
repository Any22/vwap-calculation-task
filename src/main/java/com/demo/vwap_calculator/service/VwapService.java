package com.demo.vwap_calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.repository.PriceDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VwapService {

	private final PriceDataRepository priceDataRepository;

	public List<PriceData> getPriceData() {

		log.info("Getting all Data from repository...!");

		List<PriceDataEntity> priceDataEntityList = priceDataRepository.findAll();
		log.info("The PriceDTO is {}" + priceDataEntityList);

		List<PriceData> PriceDTOList = priceDataEntityList.stream().map(priceEntity -> this.maptoDTO(priceEntity))
				.collect(Collectors.toList());

		return PriceDTOList;

	}

	private PriceData maptoDTO(PriceDataEntity pd) {

		return PriceData.builder().entryNumber(pd.getEntryNumber()).timeStamp(pd.getTimeStamp())
				.currencyPair(pd.getCurrencyPair()).price(pd.getPrice()).volume(pd.getVolume()).build();

	}

	public List<PriceDataResponse> calculateHourlyVwap(List<PriceData> priceData) {
		List<PriceDataResponse> vwapList = new ArrayList<>();

		Map<String, Map<Integer, List<PriceData>>> groupedData = priceData.stream()
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

		return vwapList;

	}

}
