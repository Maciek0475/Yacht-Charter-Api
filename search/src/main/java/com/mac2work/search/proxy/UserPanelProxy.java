package com.mac2work.search.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="USER-PANEL", configuration= UserPanelProxyErrorDecoder.class)
public interface UserPanelProxy {
	
	@GetMapping("/user/authorization/is-admin/{path}/{mappingMethod}")
	public boolean isAdmin(@PathVariable String path, @PathVariable String mappingMethod);
	
	

}
