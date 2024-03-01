package com.mac2work.search.response;

import java.util.List;

import com.mac2work.search.model.Propulsion;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YachtResponse {
	private String model;
	@Enumerated
	private Propulsion propulsion;
	private Double length;
	private Integer capacity;
	private Double motorPower;
	private Double priceFrom;
	@Enumerated
	private List<AccessoryResponse> accessories;

}
