package com.mac2work.orders.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
	
	
    @NotNull(message = "userId is obligatory")
	private Long userId;
    @NotNull(message = "yachtId is obligatory")
	private Long yachtId;
    @NotNull(message = "days are obligatory")
	private Integer days;
    @NotNull(message = "dateFrom is obligatory")
	private LocalDate dateFrom;
    @NotNull(message = "dateTo is obligatory")
	private LocalDate dateTo;
    @NotNull(message = "price is obligatory")
	private Double price;
}
