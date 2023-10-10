package com.mac2work.search.service;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.mac2work.search.model.Propulsion;
import com.mac2work.search.model.Yacht;
import com.mac2work.search.repository.YachtRepository;
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final YachtRepository yachtRepository;
	
	private YachtResponse mapToYachtResponse(Yacht yacht) {
		return YachtResponse.builder()
		.model(yacht.getModel())
		.propulsion(yacht.getPropulsion())
		.length(yacht.getLength())
		.capacity(yacht.getCapacity())
		.motorPower(yacht.getMotorPower())
		.accessories(yacht.getAccessories())
			.build();
	}

	public List<YachtResponse> getYachts() {
		
		return yachtRepository.findAll().stream()
				.map(yacht -> mapToYachtResponse(yacht)).toList();
	}
	
	public List<YachtResponse> getMotorYachtsByPropulsion(Propulsion propulsion) {
		List<Yacht> yachts = yachtRepository.findAllByPropulsion(propulsion);
		return yachts.stream()
				.map(yacht -> mapToYachtResponse(yacht)).toList();
	}
	
	public YachtResponse getYachtById(Long id) {
		Yacht yacht = yachtRepository.findById(id).orElseThrow();
		return mapToYachtResponse(yacht);
	}


}
