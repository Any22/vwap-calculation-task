package com.demo.vwap_calculator.validate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
 private String field;
 private String msg;
}
