package com.restFulApi.DemoApi.UserPosts;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
		
	}
	
	@GetMapping(path = "/users")
	public List<User> getAllUsers(){
		return service.AllUsers();
	}
	
	@GetMapping(path ="/users/{id}")
	public EntityModel<User> UserId(@PathVariable int id) {
		User u =  service.FindUser(id);
		if(u == null)
			throw new UserNotFoundException("id: "+id);
		EntityModel<User> entityModel = EntityModel.of(u);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@DeleteMapping(path ="/users/{id}")
	public void DeleteByUserId(@PathVariable int id) {
		User u =  service.FindUser(id);
		if(u == null)
			throw new UserNotFoundException("id: "+id);
		else {
			service.DeleteUser(id);
		}
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		service.saveUser(user);
		//to get the uri of created resource
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	

}
