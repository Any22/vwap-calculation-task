package com.demo.vwap_calculator.service;

import com.demo.vwap_calculator.dto.PriceData;
import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;
import com.demo.vwap_calculator.repository.PriceDataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VwapServiceTest {

	@InjectMocks
	private VwapService vwapService;

	@Mock
	private PriceDataRepository priceDataRepository;

	private PriceData priceData1;
	private PriceData priceData2;
	private PriceData priceData3;
	private PriceData priceData4;

	private PriceDataEntity priceDataEntity1;

	private PriceDataResponse priceDataResponse;

	@BeforeEach
	void setUp() {
		priceData1 = PriceData.builder().entryNumber(1).currencyPair("AUD/USD").timeStamp("05:01 AM").price(0.646)
				.volume(210).build();

		priceData2 = PriceData.builder().entryNumber(2).currencyPair("NZD/JPY").timeStamp("05:02 AM").price(0.136)
				.volume(497).build();

		priceData3 = PriceData.builder().entryNumber(3).currencyPair("AUD/USD").timeStamp("07:01 AM").price(0.345)
				.volume(239).build();

		priceData4 = PriceData.builder().entryNumber(4).currencyPair("NZD/JPY").timeStamp("07:03 AM").price(0.894)
				.volume(3454).build();

	}

	@Test
	public void get_PriceData_invokesRepository() {

		priceDataEntity1 = PriceDataEntity.builder().entryNumber(priceData1.getEntryNumber())
				.currencyPair(priceData1.getCurrencyPair()).timeStamp(priceData1.getTimeStamp())
				.price(priceData1.getPrice()).volume(priceData1.getVolume()).build();

		List<PriceDataEntity> priceDataEntityExpectedList = Arrays.asList(priceDataEntity1);

		// Mocking the repository response
		when(priceDataRepository.findAll()).thenReturn(priceDataEntityExpectedList);

		// Fetch employees via service
		List<PriceData> priceDataEntityActualList = vwapService.getPriceData();

		assertNotNull(priceDataEntityExpectedList);
		assertEquals(1, priceDataEntityExpectedList.size(), "The size of the employee list should be 1");
		assertEquals(priceDataEntityExpectedList.get(0).getCurrencyPair(),
				priceDataEntityActualList.get(0).getCurrencyPair());

	}

	@Test
	public void testCalculateHourlyVwap_toCheck_TheVWAPCalculation_IsRight() {
		List<PriceData> priceDataList = new ArrayList<>();
		priceDataList.add(priceData1);
		priceDataList.add(priceData2);
		priceDataList.add(priceData3);
		priceDataList.add(priceData4);

		List<PriceDataResponse> result = vwapService.calculateHourlyVwap(priceDataList);
		assertNotNull(result);
		assertEquals(4, result.size(), "only 4 unique time stamp with unique currency pair is obtained");
		assertEquals("NZD/JPY", result.get(0).getUniqueCurrencyPair());
		assertEquals("NZD/JPY", result.get(1).getUniqueCurrencyPair());
		assertEquals(5, result.get(0).getHourlyData());
		assertEquals(7, result.get(1).getHourlyData());
		assertTrue(result.get(0).getHourlyData() > 0);
		assertTrue(result.get(1).getHourlyData() > 0);
		assertTrue(result.get(2).getHourlyData() > 0);
		assertTrue(result.get(3).getHourlyData() > 0);

	}

}
