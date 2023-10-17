package com.mac2work.orders.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
	
	private String yachtModel;
	private Integer days;
	private Double price;
}
