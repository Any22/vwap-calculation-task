package com.demo.vwap_calculator.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
public class PriceResponse {
	
	@JsonProperty(value="ResposneWithPagination")
	private List<PriceDataResponse> priceDataResponse;

	
}
