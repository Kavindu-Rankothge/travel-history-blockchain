package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.Person;

public interface PersonService {

	public abstract String savePerson(Person person);
	public abstract Optional<Person> getPerson(String id);
	public abstract List<Person> getAllPeople();
	public abstract String updatePerson(Person person);
	public abstract String deletePerson(String id);

}
