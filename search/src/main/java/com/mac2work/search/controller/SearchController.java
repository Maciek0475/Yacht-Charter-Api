package com.mac2work.search.controller;

import java.time.LocalDate;
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

import com.mac2work.search.model.Propulsion;
import com.mac2work.search.request.YachtRequest;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.response.PriceResponse;
import com.mac2work.search.response.YachtResponse;
import com.mac2work.search.service.SearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/{id}/model")
	public ResponseEntity<String> getYachtModel(@PathVariable Long id){
		YachtResponse yachtResponse = searchService.getYachtById(id);
		return new ResponseEntity<>(yachtResponse.getModel(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<YachtResponse>> getYachts(){
		List<YachtResponse> yachts = searchService.getYachts();
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	@GetMapping("/motor")
	public ResponseEntity<List<YachtResponse>> getMotorYachts(){
		List<YachtResponse> yachts = searchService.getYachtsByPropulsion(Propulsion.MOTOR);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	@GetMapping("/sailing")
	public ResponseEntity<List<YachtResponse>> getSailingYachts(){
		List<YachtResponse> yachts = searchService.getYachtsByPropulsion(Propulsion.SAILING);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<YachtResponse> getYachtById(@PathVariable Long id){
		YachtResponse yacht = searchService.getYachtById(id);
		return new ResponseEntity<>(yacht, HttpStatus.OK);
	}
	@GetMapping("/{id}/price/{from}/{to}")
	public ResponseEntity<PriceResponse> getYachtPrice(@PathVariable Long id, @PathVariable LocalDate from, @PathVariable LocalDate to){
		PriceResponse priceResponse = searchService.getPrice(id, from, to);
		return new ResponseEntity<>(priceResponse, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<YachtResponse> addYacht(@Valid @RequestBody YachtRequest yachtRequest){
		YachtResponse yachtResponse = searchService.addYacht(yachtRequest);
		return new ResponseEntity<>(yachtResponse, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<YachtResponse> updateYacht(@Valid @RequestBody YachtRequest yachtRequest, @PathVariable Long id){
		YachtResponse yachtResponse = searchService.updateYacht(yachtRequest, id);
		return new ResponseEntity<>(yachtResponse, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteYacht(@PathVariable Long id){
		ApiResponse apiResponse = searchService.deleteYacht(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}
	
}
  
