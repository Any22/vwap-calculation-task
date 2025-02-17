package com.demo.vwap_calculator.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {
	private Integer totalPriceData;
	private Integer totalPages;
	private Integer currentPage;
	@JsonProperty(value="ResponseWithPagination")
	private List<PriceDataResponse> priceDataResponse;

	
}
