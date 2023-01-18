package com.restFulApi.DemoApi.UserPosts;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.restFulApi.DemoApi.UserPost.jpa.PostRepository;
import com.restFulApi.DemoApi.UserPost.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	private UserRepository repository;
	private PostRepository postRepository;
	
	public UserJpaResource( UserRepository repository, PostRepository postRepository) {
		this.repository = repository;
		this.postRepository = postRepository;
		
	}
	
//User methods---	
	
	@GetMapping(path = "/jpa/users")
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping(path ="/jpa/users/{id}")
	public EntityModel<User> UserId(@PathVariable int id) {
		Optional<User> u =  repository.findById(id);
		if(u.isEmpty())
			throw new UserNotFoundException("id: "+id);
		EntityModel<User> entityModel = EntityModel.of(u.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@DeleteMapping(path ="/jpa/users/{id}")
	public void DeleteByUserId(@PathVariable int id) {
		repository.deleteById(id);
		
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		repository.save(user);
		//to get the uri of created resource
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
//Post methods---
	
	@GetMapping(path ="/jpa/users/{id}/posts")
	public List<Post> retrivePostForUser (@PathVariable int id) {
		Optional<User> u =  repository.findById(id);
		if(u.isEmpty())
			throw new UserNotFoundException("id: "+id);
		return u.get().getPosts();

	}
	
	@PostMapping("/jpa/users/{id}/addposts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> u =  repository.findById(id);
		if(u.isEmpty())
			throw new UserNotFoundException("id: "+id);
		
		post.setUser(u.get());
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
