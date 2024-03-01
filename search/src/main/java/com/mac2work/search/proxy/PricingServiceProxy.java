package com.mac2work.search.proxy;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mac2work.search.response.PriceResponse;

@FeignClient(name="PRICING-SERVICE")
public interface PricingServiceProxy {

	@GetMapping("/pricing/{priceFrom}/{from}/{to}")
	public PriceResponse getCalculatedPrice(
			@PathVariable Double priceFrom, 
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to);
		
}
