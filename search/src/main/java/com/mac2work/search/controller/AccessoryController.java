package com.mac2work.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.search.request.AccessoryRequest;
import com.mac2work.search.response.AccessoryResponse;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.service.AccessoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search/accessory")
public class AccessoryController {
	
	private final AccessoryService accessoryService;	
	
	@GetMapping
	public ResponseEntity<List<AccessoryResponse>> getAccessories(){
		List<AccessoryResponse> accessoryResponses = accessoryService.getAccessories();
		return new ResponseEntity<>(accessoryResponses, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<AccessoryResponse> getAccessoryById(@PathVariable Long id){
		AccessoryResponse accessoryResponse = accessoryService.getAccessoryById(id);
		return new ResponseEntity<>(accessoryResponse, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<AccessoryResponse> addAccessory(@Valid @RequestBody AccessoryRequest accessoryRequest){
		AccessoryResponse accessoryResponse = accessoryService.addAccessory(accessoryRequest);
		return new ResponseEntity<>(accessoryResponse, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<AccessoryResponse> updateAccessory(@PathVariable Long id, @RequestBody AccessoryRequest accessoryRequest){
		AccessoryResponse accessoryResponse = accessoryService.updateAccessory(accessoryRequest, id);
		return new ResponseEntity<>(accessoryResponse, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteAccessory(@PathVariable Long id){
		ApiResponse apiResponse = accessoryService.deleteAccessory(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}

}
