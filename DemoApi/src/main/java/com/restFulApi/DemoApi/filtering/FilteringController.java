package com.restFulApi.DemoApi.filtering;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restFulApi.DemoApi.UserPosts.User;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public User Filtering() {
		return new User(5,"Luis",LocalDate.now().minusYears(32).minusDays(65));
				
	}

}
