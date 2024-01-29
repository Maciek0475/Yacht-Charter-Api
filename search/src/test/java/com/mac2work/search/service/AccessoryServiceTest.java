package com.mac2work.search.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import com.mac2work.search.proxy.UserPanelProxy;
import com.mac2work.search.repository.AccessoryRepository;
import com.mac2work.search.request.AccessoryRequest;
import com.mac2work.search.response.AccessoryResponse;
import com.mac2work.search.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class AccessoryServiceTest {
		
	@Mock
	private AccessoryRepository accessoryRepository;
	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private AccessoryService accessoryService;
	
	private Accessory accessory;
	private Accessory accessory2;
	private AccessoryRequest accessoryRequest;
	private AccessoryResponse accessoryResponse;
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
		accessoryRequest = AccessoryRequest.builder()
				.name("tent")
				.build();
		accessoryResponse = AccessoryResponse.builder()
				.name("tent")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Accessory delted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void accessoryService_getAccessories_ReturnMoreThanOneAccessoryResponse() {
		when(userPanelProxy.isAdmin("search/accessory", "get")).thenReturn(Boolean.TRUE);
		when(accessoryRepository.findAll()).thenReturn(List.of(accessory, accessory2));
		
		List<AccessoryResponse> accessoryResponses = accessoryService.getAccessories();
		
		assertThat(accessoryResponses.size()).isGreaterThan(1);
	}

	@Test
	final void accessoryService_getAccessoryById_ReturnAccessoryResponse() {
		when(userPanelProxy.isAdmin("search/accessory/"+id, "get")).thenReturn(Boolean.TRUE);
		when(accessoryRepository.findById(id)).thenReturn(Optional.of(accessory));
		
		AccessoryResponse accessoryResponse = accessoryService.getAccessoryById(id);
		
		assertThat(accessoryResponse).isEqualTo(this.accessoryResponse);
	}

	@Test
	final void accessoryService_addAccessory_ReturnAccessoryResponse() {
		when(userPanelProxy.isAdmin("search/accessory", "post")).thenReturn(Boolean.TRUE);
		when(accessoryRepository.save(any(Accessory.class))).thenReturn(accessory);
		
		AccessoryResponse accessoryResponse = accessoryService.addAccessory(accessoryRequest);

		assertThat(accessoryResponse).isEqualTo(this.accessoryResponse);
	}

	@Test
	final void accessoryService_updateAccessory_ReturnAccessoryResponse() {
		Long id = 2L;
		when(userPanelProxy.isAdmin("search/accessory/"+id, "put")).thenReturn(Boolean.TRUE);
		when(accessoryRepository.findById(id)).thenReturn(Optional.of(accessory2));
		
		AccessoryResponse accessoryResponse = accessoryService.updateAccessory(accessoryRequest, id);
		
		assertThat(accessoryResponse).isEqualTo(this.accessoryResponse);
	}

	@Test
	final void accessoryService_deleteAccessory_ReturnApiResponse() {
		when(userPanelProxy.isAdmin("search/accessory/"+id, "delete")).thenReturn(Boolean.TRUE);
		when(accessoryRepository.findById(id)).thenReturn(Optional.of(accessory));
		
		ApiResponse apiResponse = accessoryService.deleteAccessory(id);
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
	}

}
