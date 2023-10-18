package com.mac2work.search.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.search.model.Propulsion;
import com.mac2work.search.response.PriceResponse;
import com.mac2work.search.response.YachtResponse;
import com.mac2work.search.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

	private final SearchService searchService;

	@GetMapping
	public ResponseEntity<List<YachtResponse>> getYachts(){
		List<YachtResponse> yachts = searchService.getYachts();
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	@GetMapping("/motor")
	public ResponseEntity<List<YachtResponse>> getMotorYachts(){
		List<YachtResponse> yachts = searchService.getMotorYachtsByPropulsion(Propulsion.Motor);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	@GetMapping("/sailing")
	public ResponseEntity<List<YachtResponse>> getSailingYachts(){
		List<YachtResponse> yachts = searchService.getMotorYachtsByPropulsion(Propulsion.Sailing);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<YachtResponse> getYachtById(@PathVariable Long id){
		YachtResponse yacht = searchService.getYachtById(id);
		return new ResponseEntity<>(yacht, HttpStatus.OK);
	}
	@GetMapping("/{id}/price/{from}/{to}")
	public ResponseEntity<PriceResponse> getYachtById(@PathVariable Long id, @PathVariable LocalDate from, @PathVariable LocalDate to){
		PriceResponse priceResponse = searchService.getPrice(id, from, to);
		return new ResponseEntity<>(priceResponse, HttpStatus.OK);
	}
}
  
