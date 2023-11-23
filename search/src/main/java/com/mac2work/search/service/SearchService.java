package com.mac2work.search.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.search.exception.YachtNotFoundException;
import com.mac2work.search.model.Propulsion;
import com.mac2work.search.model.Yacht;
import com.mac2work.search.proxy.PricingServiceProxy;
import com.mac2work.search.proxy.UserPanelProxy;
import com.mac2work.search.repository.YachtRepository;
import com.mac2work.search.request.YachtRequest;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.response.PriceResponse;
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final YachtRepository yachtRepository;
	private final PricingServiceProxy pricingServiceProxy;
	private final UserPanelProxy userPanelProxy;

	
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
	private Yacht getRawYachtById(Long id) {
		return yachtRepository.findById(id).orElseThrow(() -> new YachtNotFoundException("id", id));
	}
	
	private Yacht mapYachtRequestToYacht(YachtRequest yachtRequest) {
		return Yacht.builder()
				.model(yachtRequest.getModel())
				.propulsion(yachtRequest.getPropulsion())
				.length(yachtRequest.getLength())
				.capacity(yachtRequest.getCapacity())
				.motorPower(yachtRequest.getMotorPower())
				.priceFrom(yachtRequest.getPriceFrom())
				.accessories(yachtRequest.getAccessories())
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
		Yacht yacht = yachtRepository.findById(id).orElseThrow(() -> new YachtNotFoundException("id", id));
		return mapToYachtResponse(yacht);
	}
	
	public PriceResponse getPrice(Long id, LocalDate from, LocalDate to) {
		Yacht yacht = getRawYachtById(id);
		PriceResponse priceResponse = pricingServiceProxy.getCalculatedPrice(yacht.getPriceFrom(), from, to);
		return PriceResponse.builder()
				.yachtModel(yacht.getModel())
				.days(priceResponse.getDays())
				.price(priceResponse.getPrice())
				.build();
	}
	public YachtResponse addYacht(YachtRequest yachtRequest) {
		userPanelProxy.isAdmin("search", "post");
		Yacht yacht = mapYachtRequestToYacht(yachtRequest);
		yachtRepository.save(yacht);
		
		return mapToYachtResponse(yacht);
	}
	public YachtResponse updateYacht(YachtRequest yachtRequest, Long id) {
		userPanelProxy.isAdmin("search", "put");
		Yacht yacht = yachtRepository.findById(id).orElseThrow(() -> new YachtNotFoundException("id", id));
		yacht.setModel(yachtRequest.getModel());
		yacht.setPropulsion(yachtRequest.getPropulsion());
		yacht.setLength(yachtRequest.getLength());
		yacht.setCapacity(yachtRequest.getCapacity());
		yacht.setMotorPower(yachtRequest.getMotorPower());
		yacht.setPriceFrom(yachtRequest.getPriceFrom());
		yacht.setAccessories(yachtRequest.getAccessories());
		yachtRepository.save(yacht);
		
		Yacht updatedYacht = yachtRepository.findById(id).orElseThrow(() -> new YachtNotFoundException("id", id));
		return mapToYachtResponse(updatedYacht);		
	}
	public ApiResponse deleteYacht(Long id) {
		userPanelProxy.isAdmin("search", "delete");
		Yacht yacht = yachtRepository.findById(id).orElseThrow(() -> new YachtNotFoundException("id", id));
		yachtRepository.delete(yacht);
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Yacht deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}
	


}
