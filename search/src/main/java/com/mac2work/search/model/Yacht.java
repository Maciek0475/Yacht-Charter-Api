package com.mac2work.search.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Yacht {
	
	private Long id;
	private String model;
	@Enumerated
	private Propulsion propulsion;
	private Double length;
	private Integer capacity;
	private Double motorPower;
	@Enumerated
	private List<Accessory> accesories;
}
