package com.demo.vwap_calculator.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class PriceData {
	  private Integer entryNumber;
	  private String timeStamp;
      private String currencyPair;
      private double price;
      private int volume;
      
      public int getHour() {
          // Using a formatter with locale to parse AM/PM and 12-hour clock
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
          LocalTime time = LocalTime.parse(this.timeStamp, formatter);
          return time.getHour();
      }

}
