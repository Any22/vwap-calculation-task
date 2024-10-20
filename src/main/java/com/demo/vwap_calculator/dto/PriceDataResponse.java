package com.demo.vwap_calculator.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDataResponse {
	private String uniqueCurrencyPair;
	private Integer hourlyData;
	private Double vwap;
}
