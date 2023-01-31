package com.example.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.api.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
