package com.mac2work.pricing.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.mac2work.pricing.model.PriceMultiplicator;
import com.mac2work.pricing.response.PricingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {
	
	public PricingResponse calculatePrice(Double priceFrom, LocalDate from, LocalDate to) {
		int days = to.getDayOfYear() - from.getDayOfYear();
		Double price = priceFrom * calculateMultiplicator(days);

		return PricingResponse.builder()
				.price(price)
				.days(days)
				.build();
	}
	
	public Double calculateMultiplicator(int days) {
		Double multiplicator = 0.0;
		if(days >= 14) {
			multiplicator += days/7 * 7 * PriceMultiplicator.ABOVE_WEEK.getMultiplicator();
			days -= days/7 * 7;
		}else if(days >= 7) {
			days -= 7;
			multiplicator += 7 * PriceMultiplicator.WEEK.getMultiplicator();
		}
			multiplicator += days * PriceMultiplicator.UNDER_WEEK.getMultiplicator();
			
		return multiplicator;
	}

}
