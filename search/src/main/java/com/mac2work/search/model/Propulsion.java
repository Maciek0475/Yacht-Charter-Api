package com.mac2work.search.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Propulsion {
	
	MOTOR,
	SAILING;
	
	@JsonValue
	public String toString() {
		return super.toString().toLowerCase();
	}
	

}
