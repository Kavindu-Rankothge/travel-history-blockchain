package com.example.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.User;
import com.example.api.service.UserService;

@RestController
public class UserConroller {

	@Autowired
	private UserService userService;

	@PostMapping("/saveUser")
	public String saveUser(@RequestBody User person) {
		return userService.saveUser(person);
	}

	@GetMapping("/getUser/{name}")
	public Optional<User> getUser(@PathVariable String name) {
		return userService.getUser(name);
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@DeleteMapping("/deleteUser/{name}")
	public String deleteUser(@PathVariable String name) {
		return userService.deleteUser(name);
	}

}