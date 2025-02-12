package com.demo.vwap_calculator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "price_data_table", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"currency_pair", "hour"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDataEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entry_number", nullable = false)
	private Integer entryNumber;

	@Column(name = "time_stamp", nullable = false)
	private String timeStamp;

	@Column(name = "currency_pair", nullable = false)
	private String currencyPair;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "volume", nullable = false)
	private int volume;

	@Column(name = "hour")
	private int hour;
}
