package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.model.Person;
import com.example.api.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public String savePerson(Person person) {
		personRepository.save(person);
		return "Added person with id: " + person.getId();		
	}

	@Override
	public Optional<Person> getPerson(int id) {
		return personRepository.findById(id);
	}

	@Override
	public List<Person> getAllPeople() {
		return personRepository.findAll();
	}

	@Override
	public String deletePerson(int id) {
		personRepository.deleteById(id);
		return "Deleted person with id: " + id;
	}

}
