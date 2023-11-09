package com.mac2work.search.request;

import java.util.List;

import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@Size(max = 7)
    @NotBlank(message = "propulsion is obligatory")
	private Propulsion propulsion;
	@Size(max = 5)
	@Pattern(regexp = "^\\d+\\.\\d+$", message ="length must be in format: ##.##")
    @NotBlank(message = "length is obligatory")
	private Double length;
	@Size(max = 3)
	@Pattern(regexp = "^\\d+$", message = "capacity must be a number")
    @NotBlank(message = "capacity is obligatory")
	private Integer capacity;
	@Size(max = 8)
	@Pattern(regexp = "^\\d+\\.\\d+$", message ="motorPower must be in format: ##.##")
    @NotBlank(message = "motorPower is obligatory")
	private Double motorPower;
	@Size(max = 11)
	@Pattern(regexp = "^\\d+\\.\\d+$", message ="priceFrom must be in format: ##.##")
    @NotBlank(message = "priceFrom is obligatory")
	private Double priceFrom;
	private List<Accessory> accessories;

}
