package com.restFulApi.DemoApi.UserPosts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	List<User> users = new ArrayList<>();
	int count = 0;
	public UserDaoService(){
		 users.add(new User(++count,"Jennie",LocalDate.now().minusYears(25)));
		 users.add(new User(++count,"Linson",LocalDate.now().minusYears(30).minusMonths(4).minusDays(15)));
		 users.add(new User(++count,"Maria",LocalDate.now().minusYears(28).minusMonths(9).minusDays(5)));
		 users.add(new User(++count,"Leo",LocalDate.now().minusYears(25).minusDays(67)));
	}
	
	public List<User> AllUsers(){
		return users;
	}
	
	public User FindUser(int id) {
		Predicate<? super User> predicate = user -> user.getId()==id;
		return users.stream().filter(predicate).findFirst().orElse(null);
	}

	public User saveUser(User user) {
		user.setId(++count);
		users.add(user);
		return user;
		
	}

	public void DeleteUser(int id) {
		Predicate<? super User> predicate = user -> user.getId()==id;
		users.removeIf(predicate);
		
	}
	
	
	

}
