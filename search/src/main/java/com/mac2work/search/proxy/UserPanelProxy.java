package com.mac2work.search.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="USER-PANEL")
public interface UserPanelProxy {
	
	@GetMapping("/user/authorization/is-admin")
	public boolean isAdmin(String path, String mappingMethod);
	
	

}
