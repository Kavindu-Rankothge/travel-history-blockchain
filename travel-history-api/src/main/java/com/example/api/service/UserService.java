package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.User;

public interface UserService {

	public abstract String saveUser(User user);
	public abstract Optional<User> getUser(String name);
	public abstract List<User> getAllUsers();
	public abstract String deleteUser(String name);

}
