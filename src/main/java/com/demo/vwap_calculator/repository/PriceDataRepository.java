package com.demo.vwap_calculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.demo.vwap_calculator.dto.PriceDataResponse;
import com.demo.vwap_calculator.entity.PriceDataEntity;

@Repository
public interface PriceDataRepository extends JpaRepository<PriceDataEntity, Integer>{

	//PriceDataEntity findByHour(String timeInHours);


}
