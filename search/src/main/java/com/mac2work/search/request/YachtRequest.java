package com.mac2work.search.request;

import java.util.List;

import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YachtRequest {
	
	
	private String model;
	@Enumerated(EnumType.STRING)
	private Propulsion propulsion;
	private Double length;
	private Integer capacity;
	private Double motorPower;
	private Double priceFrom;
	private List<Accessory> accessories;

}
