package com.demo.vwap_calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.vwap_calculator.entity.PriceDataResponseEntity;

@Repository
public interface PriceDataResponseEntityRepository extends JpaRepository<PriceDataResponseEntity, Integer>{

}
