package com.mac2work.search.request;

import java.util.List;

import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YachtRequest {
	
	@Size(max = 45)
    @NotBlank(message = "model is obligatory")
	private String model;
	@Enumerated(EnumType.STRING)
    @NotNull(message = "propulsion is obligatory")
	private Propulsion propulsion;
    @NotNull(message = "length is obligatory")
	private Double length;
    @NotNull(message = "capacity is obligatory")
	private Integer capacity;
	private Double motorPower;
    @NotNull(message = "priceFrom is obligatory")
	private Double priceFrom;
	private List<Accessory> accessories;

}
