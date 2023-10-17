package com.mac2work.orders.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {

	private Long userId;
	private Long yachtId;
	private Integer days;
	private Double price;
}
