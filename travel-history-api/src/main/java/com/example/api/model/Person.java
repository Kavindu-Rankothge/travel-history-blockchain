package com.example.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Person")
public class Person {
	@Id
	private int id;
	private String name;
	private int age;
	private String birthCountry;	
	
	public Person(int id, String name, int age, String birthCountry) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthCountry = birthCountry;
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
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getBirthCountry() {
		return birthCountry;
	}
	
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", birthCountry=" + birthCountry + "]";
	}	
	
}
