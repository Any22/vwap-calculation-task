package com.demo.vwap_calculator.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**********************************************************************************************************************
 * Added the @CreationTimestamp annotation. It ensures the calculatedAt field is automatically populated with the
 * current timestamp when the entity is first persisted.
 * The updatable = false parameter prevents Hibernate from updating this field on subsequent updates.
 * @author saba akhtar
 *********************************************************************************************************************/
@Entity
@Table(name = "vwap_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDataResponseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer entryId;
	
	@Column(name = " unique_currency_pair ", nullable = false)
	private String uniqueCurrencyPair;
	
	@Column(name = "hourly_data", nullable = false)
	private Integer hourlyData;
	
	@Column(name = "vwap", nullable = false)
	private Double vwap;
	
	@CreationTimestamp
    @Column(name = "calculated_at", updatable = false, nullable = false)
    private LocalDateTime calculatedAt;
// indexing is left 
}
