package com.mac2work.search.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;
import com.mac2work.search.model.Yacht;
import com.mac2work.search.proxy.PricingServiceProxy;
import com.mac2work.search.proxy.UserPanelProxy;
import com.mac2work.search.repository.YachtRepository;
import com.mac2work.search.request.YachtRequest;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.response.PriceResponse;
import com.mac2work.search.response.YachtResponse;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
	
	@Mock
	private YachtRepository yachtRepository;
	@Mock
	private PricingServiceProxy pricingServiceProxy;
	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private SearchService searchService;
	
	private Yacht yacht;
	private Yacht yacht2;
	private YachtRequest yachtRequest;
	private YachtResponse yachtResponse;
	
	private Accessory accessory;
	private Accessory accessory2;
	
	private PriceResponse priceResponse;
	private ApiResponse apiResponse;
	
	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1L;

		accessory = Accessory.builder()
				.name("tent")
				.build();
		accessory2 = Accessory.builder()
				.name("sink")
				.build();
		yacht = Yacht.builder()
				.id(1L)
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		yacht2 = Yacht.builder()
				.id(2L)
				.model("Orion")
				.propulsion(Propulsion.SAILING)
				.length(5.93)
				.capacity(5)
				.motorPower(5.0)
				.priceFrom(130.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		yachtRequest = YachtRequest.builder()
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		yachtResponse = YachtResponse.builder()
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		priceResponse = PriceResponse.builder()
				.yachtModel("Sasanka")
				.days(10)
				.price(1722.5)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Yacht deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void searchService_getYachts_ReturnMoreThanOneYachtResponse() {
		when(yachtRepository.findAll()).thenReturn(List.of(yacht, yacht2));
		
		List<YachtResponse> yachtResponses = searchService.getYachts();
		
		assertThat(yachtResponses.size()).isGreaterThan(1);
	}

	@Test
	final void searchService_getMotorYachtsByPropulsion_ReturnMoreThanOneYachtResponse() {
		when(yachtRepository.findAllByPropulsion(Propulsion.SAILING)).thenReturn(List.of(yacht, yacht2));
		
		List<YachtResponse> yachtResponses = searchService.getYachtsByPropulsion(Propulsion.SAILING);
		
		assertThat(yachtResponses.size()).isGreaterThan(1);
	}

	@Test
	final void searchService_getYachtById_ReturnYachtResponse() {
		when(yachtRepository.findById(id)).thenReturn(Optional.of(yacht));
		
		YachtResponse yachtResponse = searchService.getYachtById(id);
		
		assertThat(yachtResponse).isEqualTo(this.yachtResponse);
	}

	@Test
	final void searchService_getPrice_ReturnPriceResponse() {
		LocalDate from = LocalDate.of(2024, 4, 15);
		LocalDate to = LocalDate.of(2024, 4, 25);
		when(yachtRepository.findById(id)).thenReturn(Optional.of(yacht));
		when(pricingServiceProxy.getCalculatedPrice(yacht.getPriceFrom(), from, to)).thenReturn(priceResponse);
		
		PriceResponse priceResponse = searchService.getPrice(id, from, to);
		
		assertThat(priceResponse).isEqualTo(this.priceResponse);
	}

	@Test
	final void searchService_addYacht_ReturnYachtResponse() {
		when(userPanelProxy.isAdmin("search", "post")).thenReturn(true);
		when(yachtRepository.save(any(Yacht.class))).thenReturn(yacht);
		
		YachtResponse yachtResponse = searchService.addYacht(yachtRequest);
		
		assertThat(yachtResponse).isEqualTo(this.yachtResponse);
	}

	@Test
	final void searchService_updateYacht_ReturnYachtResponse() {
		Long id = 2L;
		when(userPanelProxy.isAdmin("search", "put")).thenReturn(true);
		when(yachtRepository.findById(id)).thenReturn(Optional.of(yacht2));
		when(yachtRepository.save(any(Yacht.class))).thenReturn(yacht);

		YachtResponse yachtResponse = searchService.updateYacht(yachtRequest, id);
		
		assertThat(yachtResponse).isEqualTo(this.yachtResponse);
	}

	@Test
	final void searchService_deleteYacht_ReturnApiResponse() {
		when(userPanelProxy.isAdmin("search", "delete")).thenReturn(true);
		when(yachtRepository.findById(id)).thenReturn(Optional.of(yacht));
		doNothing().when(yachtRepository).delete(yacht);
		
		ApiResponse apiResponse = searchService.deleteYacht(id);
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
	}

}
