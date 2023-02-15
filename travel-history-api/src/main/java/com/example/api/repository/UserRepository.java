package com.example.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.api.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}