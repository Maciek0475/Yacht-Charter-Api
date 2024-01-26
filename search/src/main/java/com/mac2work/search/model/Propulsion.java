package com.mac2work.search.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Propulsion {
	
	MOTOR,
	SAILING;
	
	public String toString() {
		return super.toString().toLowerCase();
	}
	

}
