package com.mac2work.search.model;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="yachts")
public class Yacht {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native")
	private Long id;
	private String model;
	@Enumerated(EnumType.STRING)
	private Propulsion propulsion;
	private Double length;
	private Integer capacity;
	private Double motorPower;
	@ManyToMany
	@JoinTable(
		name = "yachts_accessories",
	    joinColumns = @JoinColumn(name = "yacht_id"),
	    inverseJoinColumns = @JoinColumn(name = "accessory_id")
		    )
	private List<Accessory> accessories;
}
