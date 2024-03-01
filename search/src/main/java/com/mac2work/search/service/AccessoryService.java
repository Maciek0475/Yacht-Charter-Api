package com.mac2work.search.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.search.exception.ResourceNotFoundException;
import com.mac2work.search.model.Accessory;
import com.mac2work.search.proxy.UserPanelProxy;
import com.mac2work.search.repository.AccessoryRepository;
import com.mac2work.search.request.AccessoryRequest;
import com.mac2work.search.response.AccessoryResponse;
import com.mac2work.search.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessoryService {

	private final AccessoryRepository accessoryRepository;
	private final UserPanelProxy userPanelProxy;
	
	public AccessoryResponse mapToAccessoryResponse(Accessory accessory) {
		return AccessoryResponse.builder()
				.name(accessory.getName()).build();
	}
	
	private Accessory mapToAccessory(AccessoryRequest accessoryRequest) {
		return Accessory.builder()
				.name(accessoryRequest.getName()).build();
	}
	
	public List<AccessoryResponse> getAccessories() {
		userPanelProxy.isAdmin("accessory", "get");
		List<Accessory> accessories = accessoryRepository.findAll();
		
		return accessories.stream().map(
				accessory -> mapToAccessoryResponse(accessory)).toList();
	}

	public AccessoryResponse getAccessoryById(Long id) {
		userPanelProxy.isAdmin("accessory", "get");
		Accessory accessory = accessoryRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Accessory", "id", id));
		
		return mapToAccessoryResponse(accessory);
	}
	
	public AccessoryResponse addAccessory(AccessoryRequest accessoryRequest) {
		userPanelProxy.isAdmin("accessory", "post");
		Accessory accessory = mapToAccessory(accessoryRequest);
		
		accessory = accessoryRepository.save(accessory);
		
		return mapToAccessoryResponse(accessory);
	}
	
	public AccessoryResponse updateAccessory(AccessoryRequest accessoryRequest, Long id) {
		userPanelProxy.isAdmin("accessory", "put");
		Accessory accessory = accessoryRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Accessory", "id", id));
		accessory.setName(accessoryRequest.getName());
		accessory = accessoryRepository.save(accessory);
		
		return mapToAccessoryResponse(accessory);
	}
	
	public ApiResponse deleteAccessory(Long id) {
		userPanelProxy.isAdmin("accessory", "delete");
		Accessory accessory = accessoryRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Accessory", "id", id));
		
		accessoryRepository.delete(accessory);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Accessory delted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}
	
}
