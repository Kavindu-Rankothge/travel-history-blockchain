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
import com.example.api.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/addPerson")
	public String savePerson(@RequestBody Person person) {
		return personService.savePerson(person);				
	}
	
	@GetMapping("/findAllPeople")
	public List<Person> getAllPeople() {
		return personService.getAllPeople();
	}

	@GetMapping("/findPerson/{id}")
	public Optional<Person> getPerson(@PathVariable int id) {
		return personService.getPerson(id);
	}

	@DeleteMapping("/delete/{id}")
	public String deletePerson(@PathVariable int id) {
		return personService.deletePerson(id);		
	}
	
}
