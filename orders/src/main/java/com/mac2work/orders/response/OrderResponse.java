package com.mac2work.orders.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
	
	private String yachtModel;
	private Integer days;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Double price;
}
