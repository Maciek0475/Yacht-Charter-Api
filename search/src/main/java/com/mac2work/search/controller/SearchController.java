package com.mac2work.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.search.model.Propulsion;
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
		List<YachtResponse> yachts = searchService.getMotorYachtsByPropulsion(Propulsion.MOTOR);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	@GetMapping("/sailing")
	public ResponseEntity<List<YachtResponse>> getSailingYachts(){
		List<YachtResponse> yachts = searchService.getMotorYachtsByPropulsion(Propulsion.SAILING);
		return new ResponseEntity<>(yachts, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<YachtResponse> getYachtById(@PathVariable Long id){
		YachtResponse yacht = searchService.getYachtById(id);
		return new ResponseEntity<>(yacht, HttpStatus.OK);
	}
}
  
