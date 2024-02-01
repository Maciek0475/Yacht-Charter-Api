package com.mac2work.orders.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mac2work.search.response.YachtResponse;


@FeignClient(name="SEARCH-SERVICE")
public interface SearchServiceProxy {

	@GetMapping("/search/{id}/model")
	public ResponseEntity<String> getYachtModel(@PathVariable Long id);
	

}
