package com.mac2work.pricing.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PriceMultiplicator {
	
	UNDER_WEEK(1.5),
	WEEK(1.25),
	ABOVE_WEEK(1.0);

	private final Double price;
	
	public Double getMultiplicator() {
		return price;
	}
	
}
