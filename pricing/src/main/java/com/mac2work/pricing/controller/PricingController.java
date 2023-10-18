package com.mac2work.pricing.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.pricing.response.PricingResponse;
import com.mac2work.pricing.service.PricingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pricing")
public class PricingController {
	
	private final PricingService pricingService;
	
	@GetMapping("/{priceFrom}/{from}/{to}")
	public PricingResponse getCalculatedPrice(@PathVariable Double priceFrom, @PathVariable LocalDate from, @PathVariable LocalDate to){
		
		PricingResponse pricingResponse = pricingService.calculatePrice(priceFrom, from, to);
		return pricingResponse;
	}
	
}
