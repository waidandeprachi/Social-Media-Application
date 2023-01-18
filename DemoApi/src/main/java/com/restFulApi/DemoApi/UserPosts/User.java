package com.restFulApi.DemoApi.UserPosts;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name= "user_details")
public class User {

	@Id
	@GeneratedValue
	private int id;
	@Size(min = 2, message = " Name should have atleast 2 characters ")
	@JsonProperty("user_name")
	private String name;
	@Past(message=" Birth Date should be in past")
	@JsonProperty("Birth_Date")
	private LocalDate dob;
	@OneToMany(mappedBy="user")
	//@JsonIgnore
	private List<Post> posts;
	
	public User() {
	}

	public User(int id, String name, LocalDate dob) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDob() {
		return dob;
	}
	
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dob=" + dob + ", posts=" + posts + "]";
	}


	
	
	
}
