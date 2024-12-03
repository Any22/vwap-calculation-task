package com.demo.vwap_calculator.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {
	private Integer entryNumber;
	private String timeStamp;
	private String currencyPair;
	private double price;
	private int volume;
	
	public int getHour() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
		LocalTime time = LocalTime.parse(this.timeStamp, formatter);
		return time.getHour();
	}

}
