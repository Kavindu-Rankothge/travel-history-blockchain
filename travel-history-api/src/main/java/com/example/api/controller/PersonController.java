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

import com.example.api.model.Person;
import com.example.api.repository.PersonRepository;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@PostMapping("/addPerson")
	public String savePerson(@RequestBody Person person) {
		personRepository.save(person);
		return "Added person with id: " + person.getId();		
	}
	
	@GetMapping("/findAllPeople")
	public List<Person> getPeople() {
		return personRepository.findAll();
	}

	@GetMapping("/findPerson/{id}")
	public Optional<Person> getPerson(@PathVariable int id) {
		return personRepository.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public String deletePerson(@PathVariable int id) {
		personRepository.deleteById(id);
		return "Deleted person with id: " + id;
	}

}
