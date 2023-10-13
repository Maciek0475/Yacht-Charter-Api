package com.mac2work.search.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceResponse {
	private String yachtModel;
	private Integer days;
	private Double price;

}
