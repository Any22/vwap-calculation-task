package com.demo.vwap_calculator.validate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.demo.vwap_calculator.dto.PriceData;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceDataValidator implements ConstraintValidator<ValidPriceData, String> {
	private FieldType fieldType;
	@Override
	public void initilialize (ValidPriceData constraintAnnotation){
		this.fieldType = constraintAnnotation
	}

	@Override
	public boolean isValid (String value, ConstraintValidatorContext context){
		if (value==null){
			return false;
		}
		switch(fieldType){
			case Time_Stamp:
				return value.matches("^[a-z]+$");
			case Last_Name:
				return value.matches("^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$");
			case Password:
				return value.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
			default:
				return false;
		}
	}
}
//	@Override
//	public void initialize(ValidPriceData constraintAnnotation) {
//		ConstraintValidator.super.initialize(constraintAnnotation);
//	}
//
//	@Override
//	public boolean isValid(PriceData priceData, ConstraintValidatorContext context) {
//
//		// Disable default violation messages
//		context.disableDefaultConstraintViolation();
//		List<Error> errorFields = new ArrayList<>();
//
//		// Validate Timestamp
//		String timeStamp = priceData.getTimeStamp();
//
//		if (timeStamp == null || !isValidTimeStamp(timeStamp)) {
//			errorFields.add(new Error("timeStamp", ErrorCode.CORRECT_FORMATED_TIME_STAMP_REQUIRED));
//
//		}
//
//		// Validate currencyPair
//		String currencyPair = priceData.getCurrencyPair();
//		if (currencyPair == null || !currencyPair.matches("[A-Z]{3}/[A-Z]{3}")) {
//			errorFields.add(new Error("currencyPair", ErrorCode.CORRECT_FORMATED_CURRENCY_PAIR_REQUIRED));
//		}
//

        
//     // If there are any errors, build constraint violations
//        if (!errorFields.isEmpty()) {
//            for (Error error : errorFields) {
//                context.buildConstraintViolationWithTemplate(error.getMsg())
//                       .addPropertyNode(error.getField())
//                       .addConstraintViolation();
//            }
//            return false; // Indicate validation failure
//        }
//
//        return true; // Indicate validation success
//    }

	

//	private boolean isValidTimeStamp(String timeStamp) {
//		try {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
//			formatter.parse(timeStamp); // Will throw exception if not valid
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//
//	}

//}
