package com.mac2work.search.service;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.mac2work.search.model.Yacht;
import com.mac2work.search.repository.YachtRepository;
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final YachtRepository yachtRepository;

	public List<YachtResponse> getYachts() {
		
		return yachtRepository.findAll().stream()
				.map(yacht -> YachtResponse().builder()
				.model(yacht.getModel)
				.propulsion(yacht.getPropulsion)
				.length(yacht.getLength)
				.capacity(yacht.getCapacity)
				.motorPower(yacht.getMotorPower)
				.accessories(yacht.getAccessories)
					.build()).toList();
	}

}
