package com.mac2work.pricing.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mac2work.pricing.response.PricingResponse;
import com.mac2work.pricing.service.PricingService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PricingController.class)
class PricingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PricingService pricingService;
	
	private PricingResponse pricingResponse;

	@BeforeEach
	void setUp() throws Exception {
		 pricingResponse = PricingResponse.builder()
				 .price(3312.5)
				 .days(10)
				 .build();
	}

	@Test
	final void pricingController_getCalculatedPrice_ReturnPricingResponse() throws Exception {
		Double priceFrom = 250.0;
		LocalDate from = LocalDate.of(2023, 4, 20);
		LocalDate to = LocalDate.of(2023, 4, 30);
		when(pricingService.calculatePrice(priceFrom, from, to)).thenReturn(pricingResponse);
		
		ResultActions response = mockMvc.perform(get("/pricing/"+priceFrom+"/"+from+"/"+to)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(jsonPath("$.price", CoreMatchers.is(pricingResponse.getPrice())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(pricingResponse.getDays())));
		
		
	}

}
