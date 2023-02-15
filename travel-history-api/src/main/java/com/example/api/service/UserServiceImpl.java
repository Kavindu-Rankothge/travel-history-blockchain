package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String saveUser(User user) {
		userRepository.save(user);
		return "Added user with name: " + user.getName();
	}

	@Override
	public Optional<User> getUser(String name) {
		return userRepository.findById(name);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String deleteUser(String name) {
		userRepository.deleteById(name);
		return "Deleted user with name: " + name;
	}

}