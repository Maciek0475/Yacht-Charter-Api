package com.mac2work.pricing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.pricing.response.PricingResponse;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {
	
	@InjectMocks
	private PricingService pricingService;


	@Test
	final void pricingService_calculatePrice_ReturnPricingResponse() {
		Double priceFrom = 250.0;
		LocalDate from = LocalDate.of(2023, 4, 20);
		LocalDate to = LocalDate.of(2023, 4, 30);
		Integer expectedDays = 10;
		Double multiplicator = 13.25;
		Double expectedPrice = 3312.5;
		PricingService pricingService2 = spy(pricingService);
		when(pricingService2.calculateMultiplicator(expectedDays)).thenReturn(multiplicator);
		
		PricingResponse pricingResponse = pricingService2.calculatePrice(priceFrom, from, to);

		assertThat(pricingResponse.getPrice()).isEqualTo(expectedPrice);
		assertThat(pricingResponse.getDays()).isEqualTo(expectedDays);
	}

	@Test
	final void pricingService_calculateMultiplicator_ReturnCorrectMultiplicators() {
		List<Integer> days = List.of(5, 10, 15);
		List<Double> multiplicators = List.of(7.5, 13.25, 15.5);
		
		for(int i = 0; i < days.size(); i++) {
		Double multiplicator = pricingService.calculateMultiplicator(days.get(i));
		
		assertThat(multiplicator).isEqualTo(multiplicators.get(i));
		}
		
	}

}
