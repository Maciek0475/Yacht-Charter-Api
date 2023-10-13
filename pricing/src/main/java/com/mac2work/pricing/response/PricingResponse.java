package com.mac2work.pricing.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricingResponse {
	private Integer days;
	private Double price;
}
