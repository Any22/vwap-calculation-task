package com.demo.vwap_calculator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor 
@Builder
public class PriceDataRequestOptional {
	
	private Integer page;
	private Integer pageSize;

}
