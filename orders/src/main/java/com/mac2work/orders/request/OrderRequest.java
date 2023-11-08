package com.mac2work.orders.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
	
	
	@Size(max = 11)
    @NotBlank(message = "userId is obligatory")
	private Long userId;
	@Size(max = 11)
    @NotBlank(message = "yachtId is obligatory")
	private Long yachtId;
	@Size(max = 3)
	@Pattern(regexp = "^\\d+$", message = "days must be a number")
    @NotBlank(message = "days are obligatory")
	private Integer days;
	@Pattern(regexp =  "^\\d{4}-\\d{2}-\\d{2}$", message = "date format is YYYY-MM-DD")
    @NotBlank(message = "from is obligatory")
	private LocalDate from;
	@Pattern(regexp =  "^\\d{4}-\\d{2}-\\d{2}$", message = "date format is YYYY-MM-DD")
    @NotBlank(message = "to is obligatory")
	private LocalDate to;
	@Pattern(regexp = "^\\d+\\.\\d+$", message ="price must be in format: ##.##")
    @NotBlank(message = "price is obligatory")
	private Double price;
}
