package com.mac2work.orders.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="USER-PANEL")
public interface UserPanelProxy {
	
	@GetMapping("user/auth/id")
	public Long getLoggedInUserId();

}
