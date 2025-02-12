package com.demo.vwap_calculator.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.demo.vwap_calculator.validate.FieldType;
import com.demo.vwap_calculator.validate.ValidPriceData;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
	@ValidPriceData(type= FieldType.Time_Stamp)
	private String timeStamp;
	@NotNull(message = "Currency pair cannot be null")
	private String currencyPair;
	@Positive(message = "Price must be greater than zero")
	private double price;
	@Positive(message = "Volume must be greater than zero")
	private double volume;
	
	public int getHour() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
		LocalTime time = LocalTime.parse(this.timeStamp, formatter);
		return time.getHour();
	}

}
